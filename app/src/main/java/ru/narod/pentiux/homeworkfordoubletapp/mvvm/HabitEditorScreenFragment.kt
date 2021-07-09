package ru.narod.pentiux.homeworkfordoubletapp.mvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import ru.narod.pentiux.homeworkfordoubletapp.R
import ru.narod.pentiux.homeworkfordoubletapp.databinding.FragmentHabitEditorScreenBinding
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.HabitPrioritySpinnerAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.RadioButtonHabitTypeAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData


class HabitEditorScreenFragment : Fragment(R.layout.fragment_habit_editor_screen) {

    private var _binding: FragmentHabitEditorScreenBinding? = null
    private val binding get() = checkNotNull(_binding) { "MainFragment _binding isn't initialized!" }

    private val viewModel: MainHabitsViewModel by activityViewModels()
    private val args: HabitEditorScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHabitEditorScreenBinding.bind(view)
        val habit = args.habitCharacteristics

        if (habit.isDataEmptyOrBlank()) setSpinnerAndRadio(habit) else fillAllFields(habit)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setSpinnerAndRadio(habit: HabitCharacteristicsData) = binding.run {
        fhesPrioritySpinner.onItemSelectedListener =
            HabitPrioritySpinnerAdapter(habit, fhesPrioritySpinner, root.context)
        RadioButtonHabitTypeAdapter(fhesHabitLayoutRadioGroup, habit, root.context)
    }

    private fun fillAllFields(habit: HabitCharacteristicsData) = binding.run {
        fhesHabitNameInput.setText(habit.name)
        fhesHabitColor.setBackgroundColor(habit.color)
        fhesHabitDescriptionInput.setText(habit.description)
        fhesHabitatFrequencyInput.setText(habit.frequency)
        setSpinnerAndRadio(habit)
    }

    private fun initializeButtons() {
        //TODO сохранение в базу по нажатии на Save
        //TODO выход назад по нажатии на Cancel
        //TODO проверка всели поля заполнены
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}