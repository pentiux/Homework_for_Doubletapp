package ru.narod.pentiux.homeworkfordoubletapp.mvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ru.narod.pentiux.homeworkfordoubletapp.R
import ru.narod.pentiux.homeworkfordoubletapp.databinding.FragmentHabitEditorScreenBinding
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.HabitPrioritySpinnerAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.RadioButtonHabitTypeAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.EditHabitFieldState
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData


class HabitEditorScreenFragment : Fragment(R.layout.fragment_habit_editor_screen) {

    private var _binding: FragmentHabitEditorScreenBinding? = null
    private val binding get() = checkNotNull(_binding) { "MainFragment _binding isn't initialized!" }

    private val mainViewModel: MainHabitsViewModel by activityViewModels()
    private val viewModel: EditViewModel by viewModels()
    private val args: HabitEditorScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHabitEditorScreenBinding.bind(view)
        viewModel.editorHabit = args.habitCharacteristics
        with(viewModel.editorHabit) {
            if (isDataEmptyOrBlank()) setSpinnerAndRadio(this) else fillAllFields(this)
        }

        super.onViewCreated(view, savedInstanceState)
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
        //TODO сохранение в базу по нажатии на Save
        //TODO выход назад по нажатии на Cancel
        //TODO проверка всели поля заполнены
    }

    private fun setDoAfterTextChanged() = with(binding) {
        fhesHabitNameInput.doAfterTextChanged {
            viewModel.name = fhesHabitNameInput.text.toString()
            when(viewModel.checkName()) {
                EditHabitFieldState.GOOD -> fhesHabitNameLabel.error = ""
                EditHabitFieldState.TOO_LONG -> fhesHabitNameLabel.error =
                    getString(R.string.name_is_too_long, nameLength)
                EditHabitFieldState.EMPTY -> fhesHabitNameLabel.error = getString(R.string.Name_cant_empty)
            }
        }
        fhesHabitDescriptionInput.doAfterTextChanged {
            viewModel.description = fhesHabitDescriptionInput.text.toString()
            when(viewModel.checkDescription()) {
                EditHabitFieldState.EMPTY -> fhesHabitDescription.error =
                    getString(R.string.description_cant_empty)
                else -> fhesHabitDescription.error = ""
            }
        }
        fhesHabitFrequencyInput.doAfterTextChanged {
            viewModel.frequency = fhesHabitDescriptionInput.text.toString()
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