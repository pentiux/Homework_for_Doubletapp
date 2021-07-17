package ru.narod.pentiux.homeworkfordoubletapp.mvvm

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
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.HabitPrioritySpinnerAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.RadioButtonHabitTypeAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.EditHabitFieldState
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.ModelStateData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.ModelStateError
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.ModelStateSuccess
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.utils.repeatOnLifecycle
import javax.inject.Inject

@AndroidEntryPoint
class HabitEditorScreenFragment() : Fragment(R.layout.fragment_habit_editor_screen) {

    @ApplicationScope
    @Inject
    lateinit var appCoroutineScope: CoroutineScope

    @MainDispatcher
    @Inject
    lateinit var mainDispatcher: CoroutineDispatcher

    private var _binding: FragmentHabitEditorScreenBinding? = null
    private val binding get() = checkNotNull(_binding) { "MainFragment _binding isn't initialized!" }

    private val mainViewModel: MainHabitsViewModel by activityViewModels()
    private val viewModel: EditViewModel by viewModels()
    private val args: HabitEditorScreenFragmentArgs by navArgs()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHabitEditorScreenBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        viewModel.editorHabit = args.habitCharacteristics
        with(viewModel.editorHabit) {
            if (isDataEmptyOrBlank()) setSpinnerAndRadio(this) else fillAllFields(this)
        }
        setDoAfterTextChanged()

        viewModel.saveState.onEach {
            binding.fhesSaveButton.isEnabled = it
        }.repeatOnLifecycle(viewLifecycleOwner)

        initializeButtons()
        viewModel.canWeSave()
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
        with(binding) {
            fhesSaveButton.setOnClickListener {
                appCoroutineScope.launch(mainDispatcher) {
                    when (mainViewModel.insertHabit(viewModel.editorHabit)) {
                        is ModelStateSuccess, is ModelStateData -> {
                            Toast.makeText(context, getString(R.string.successfully_inserted), Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }
                        is ModelStateError -> {
                            Toast.makeText(context, getString(R.string.habit_existing), Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
            fhesCancelButton.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun setDoAfterTextChanged() = with(binding) {
        fhesHabitNameInput.doAfterTextChanged {
            viewModel.name = fhesHabitNameInput.text.toString()
            viewModel.canWeSave()
            when(viewModel.checkName()) {
                EditHabitFieldState.GOOD -> fhesHabitNameLabel.error = ""
                EditHabitFieldState.TOO_LONG -> fhesHabitNameLabel.error =
                    getString(R.string.name_is_too_long, nameLength)
                EditHabitFieldState.EMPTY -> fhesHabitNameLabel.error = getString(R.string.Name_cant_empty)
            }
        }
        fhesHabitDescriptionInput.doAfterTextChanged {
            viewModel.description = fhesHabitDescriptionInput.text.toString()
            viewModel.canWeSave()
            when(viewModel.checkDescription()) {
                EditHabitFieldState.EMPTY -> fhesHabitDescription.error =
                    getString(R.string.description_cant_empty)
                else -> fhesHabitDescription.error = ""
            }
        }
        fhesHabitFrequencyInput.doAfterTextChanged {
            viewModel.frequency = fhesHabitFrequencyInput.text.toString()
            viewModel.canWeSave()
            when(viewModel.checkFrequency()) {
                EditHabitFieldState.GOOD -> fhesHabitFrequency.error = ""
                EditHabitFieldState.TOO_LONG -> fhesHabitFrequency.error =
                    getString(R.string.Frequency_too_long, frequencyLength)
                EditHabitFieldState.EMPTY -> fhesHabitFrequency.error = getString(R.string.frequency_empty)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}