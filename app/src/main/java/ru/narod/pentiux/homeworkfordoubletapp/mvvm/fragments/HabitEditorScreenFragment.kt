package ru.narod.pentiux.homeworkfordoubletapp.mvvm.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.narod.pentiux.homeworkfordoubletapp.R
import ru.narod.pentiux.homeworkfordoubletapp.databinding.FragmentHabitEditorScreenBinding
import ru.narod.pentiux.homeworkfordoubletapp.di.coroutines.ApplicationScope
import ru.narod.pentiux.homeworkfordoubletapp.di.coroutines.MainDispatcher
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.viewmodels.MainHabitsViewModel
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.HabitPrioritySpinnerAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.RadioButtonHabitTypeAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.EditHabitFieldState
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.ModelStateData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.ModelStateError
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.ModelStateSuccess
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.utils.repeatOnLifecycle
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.viewmodels.EditViewModel
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.viewmodels.frequencyLength
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.viewmodels.nameLength
import javax.inject.Inject

@AndroidEntryPoint
class HabitEditorScreenFragment : Fragment(R.layout.fragment_habit_editor_screen) {

    @ApplicationScope
    @Inject
    lateinit var appCoroutineScope: CoroutineScope

    @MainDispatcher
    @Inject
    lateinit var mainDispatcher: CoroutineDispatcher

    private var _binding: FragmentHabitEditorScreenBinding? = null
    private val binding get() = checkNotNull(_binding) { "MainFragment _binding isn't initialized!" }

    private val mainViewModel: MainHabitsViewModel by activityViewModels()
    private val viewModel: EditViewModel by activityViewModels()
    private val args: HabitEditorScreenFragmentArgs by navArgs()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHabitEditorScreenBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        viewModel.editorHabit = args.habitCharacteristics
        setDoAfterTextChanged()
        fragmentInitialization()

        viewModel.saveState.onEach {
            binding.fhesSaveButton.isEnabled = it
        }.repeatOnLifecycle(viewLifecycleOwner)

        initializeButtons()
        initializeColor()
        viewModel.canWeSave()
    }

    override fun onResume() {
        super.onResume()
        fragmentInitialization()
    }

    private fun fragmentInitialization(){
        with(viewModel.editorHabit) {
            if (isDataEmptyOrBlank()) {
                viewModel.fragmentIsBlank = true
                setSpinnerAndRadio(this)
            } else {
                viewModel.fragmentIsBlank = false
                fillAllFields(this)
            }
        }
        checkDescription()
        checkFrequency()
        checkName()
    }

    private fun initializeColor() {
        binding.fhesHabitColor.setOnClickListener {
            val action = HabitEditorScreenFragmentDirections
                .actionHabitEditorScreenToEditColorFragment(viewModel.editorHabit.color)
            findNavController().navigate(action)
        }
    }

    private fun setSpinnerAndRadio(habit: HabitCharacteristicsData) = binding.run {
        HabitPrioritySpinnerAdapter(habit, fhesPrioritySpinner, root.context)
        RadioButtonHabitTypeAdapter(fhesHabitLayoutRadioGroup, habit, root.context)
    }

    private fun fillAllFields(habit: HabitCharacteristicsData) = binding.run {
        fhesHabitNameInput.setText(habit.name)
        fhesHabitColor.setBackgroundColor(habit.color)
        fhesHabitDescriptionInput.setText(habit.description)
        fhesHabitFrequencyInput.setText(habit.frequency)
        setSpinnerAndRadio(habit)
    }

    private fun initializeButtons() {
        saveEditInitializing()
        binding.fhesCancelButton.setOnClickListener { findNavController().navigateUp() }

        binding.fhesDeleteButton.setOnClickListener {
            appCoroutineScope.launch(mainDispatcher) {
                when(mainViewModel.deleteHabit(viewModel.editorHabit)) {
                    is ModelStateSuccess, is ModelStateData -> {
                        Toast.makeText(context, getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                    is ModelStateError -> {
                        Toast.makeText(context, getString(R.string.no_such_name), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    private fun saveEditInitializing() {//if Fragment is opened by FAB do first else second
        if (viewModel.fragmentIsBlank) {
            binding.fhesSaveButton.text = getString(R.string.save)
            binding.fhesSaveButton.setOnClickListener {
                appCoroutineScope.launch(mainDispatcher) {
                    when (mainViewModel.insertHabit(viewModel.editorHabit)) {
                        is ModelStateSuccess, is ModelStateData -> {
                            Toast.makeText(context, getString(R.string.successfully_inserted), Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigateUp()
                        }
                        is ModelStateError -> {
                            Toast.makeText(context, getString(R.string.habit_existing), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

            }

        } else {
            binding.fhesSaveButton.text = getString(R.string.apply_on_save_button)
            binding.fhesSaveButton.setOnClickListener {
                appCoroutineScope.launch(mainDispatcher) {
                    when (mainViewModel.updateHabit(viewModel.editorHabit)) {
                        is ModelStateSuccess, is ModelStateData -> {
                            Toast.makeText(context, getString(R.string.successfully_inserted), Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigateUp()
                        }
                        is ModelStateError -> Toast.makeText(
                            context,
                            getString(R.string.no_such_name),
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun setDoAfterTextChanged() = with(binding) {
        fhesHabitNameInput.doAfterTextChanged {
            viewModel.editorHabit.name = fhesHabitNameInput.text.toString()
            viewModel.canWeSave()
            checkName()
        }
        fhesHabitDescriptionInput.doAfterTextChanged {
            viewModel.editorHabit.description = fhesHabitDescriptionInput.text.toString()
            viewModel.canWeSave()
            checkDescription()
        }
        fhesHabitFrequencyInput.doAfterTextChanged {
            viewModel.editorHabit.frequency = fhesHabitFrequencyInput.text.toString()
            viewModel.canWeSave()
            checkFrequency()
        }
    }

    private fun checkName() = when(viewModel.checkName()) {
        EditHabitFieldState.GOOD -> binding.fhesHabitNameLabel.error = ""
        EditHabitFieldState.TOO_LONG -> binding.fhesHabitNameLabel.error =
            getString(R.string.name_is_too_long, nameLength)
        EditHabitFieldState.EMPTY -> binding.fhesHabitNameLabel.error = getString(R.string.Name_cant_empty)
    }


    private fun checkDescription() = when(viewModel.checkDescription()) {
        EditHabitFieldState.EMPTY -> binding.fhesHabitDescription.error =
            getString(R.string.description_cant_empty)
        else -> binding.fhesHabitDescription.error = ""
    }

    private fun checkFrequency() = when(viewModel.checkFrequency()) {
        EditHabitFieldState.GOOD -> binding.fhesHabitFrequency.error = ""
        EditHabitFieldState.TOO_LONG -> binding.fhesHabitFrequency.error =
            getString(R.string.Frequency_too_long, frequencyLength)
        EditHabitFieldState.EMPTY -> binding.fhesHabitFrequency.error = getString(R.string.frequency_empty)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}