package ru.narod.pentiux.homeworkfordoubletapp.data

sealed class HabitDataStates
class HabitDataStateSuccess(val message: String) : HabitDataStates()
class HabitDataStateError(val errorMessage: String) : HabitDataStates()