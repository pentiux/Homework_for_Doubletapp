package ru.narod.pentiux.homeworkfordoubletapp.mvvm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.EditHabitFieldState
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData

internal const val nameLength = 35
internal const val frequencyLength = 35


class EditViewModel : ViewModel() {

    private var _saveState  = MutableStateFlow(false)
    val saveState get() = _saveState.asStateFlow()

    var editorHabit = HabitCharacteristicsData.getEmptyHabit()
    var name = editorHabit.name
        set(value) { field = value; editorHabit.name = value}
    var description = editorHabit.description
        set(value) { field = value; editorHabit.description = value}
    var frequency = editorHabit.frequency
        set(value) { field = value; editorHabit.frequency = value}

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

    fun canWeSave() {
        _saveState.value = checkFrequency() == EditHabitFieldState.GOOD &&
                            checkDescription() == EditHabitFieldState.GOOD &&
                            checkName() == EditHabitFieldState.GOOD
    }
}
