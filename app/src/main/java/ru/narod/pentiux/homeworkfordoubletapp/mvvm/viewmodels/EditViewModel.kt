package ru.narod.pentiux.homeworkfordoubletapp.mvvm.viewmodels

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

    var fragmentIsBlank = false

    var editorHabit = HabitCharacteristicsData.getEmptyHabit()
    var tempColor = 0

    fun checkName() = when {
        editorHabit.name.length > nameLength -> EditHabitFieldState.TOO_LONG
        editorHabit.name.isEmpty() || editorHabit.name.isBlank() -> EditHabitFieldState.EMPTY
        else -> EditHabitFieldState.GOOD
    }

    fun checkFrequency() = when {
        editorHabit.frequency.length > frequencyLength -> EditHabitFieldState.TOO_LONG
        editorHabit.frequency.isEmpty() || editorHabit.frequency.isBlank() -> EditHabitFieldState.EMPTY
        else -> EditHabitFieldState.GOOD
    }

    fun checkDescription() = when {
        editorHabit.description.isEmpty() || editorHabit.description.isBlank() -> EditHabitFieldState.EMPTY
        else -> EditHabitFieldState.GOOD
    }

    fun canWeSave() {
        _saveState.value = checkFrequency() == EditHabitFieldState.GOOD &&
                            checkDescription() == EditHabitFieldState.GOOD &&
                            checkName() == EditHabitFieldState.GOOD
    }
}
