package ru.narod.pentiux.homeworkfordoubletapp.mvvm

import androidx.lifecycle.ViewModel
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.EditHabitFieldState
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData

internal const val nameLength = 35
internal const val frequencyLength = 35


class EditViewModel : ViewModel() {

    var editorHabit = HabitCharacteristicsData.getEmptyHabit()
    var name = editorHabit.name
    var description = editorHabit.description
    var frequency = editorHabit.frequency

    fun checkName() = when {
        name.length > nameLength -> EditHabitFieldState.TOO_LONG
        name.isEmpty() || name.isBlank() -> EditHabitFieldState.EMPTY
        else -> EditHabitFieldState.GOOD
    }

    fun checkFrequency() = when {
        frequency.length > frequencyLength -> EditHabitFieldState.TOO_LONG
        frequency.isEmpty() || frequency.isBlank() -> EditHabitFieldState.EMPTY
        else -> EditHabitFieldState.GOOD
    }

    fun checkDescription() = when {
        description.isEmpty() || description.isBlank() -> EditHabitFieldState.EMPTY
        else -> EditHabitFieldState.GOOD
    }
}
