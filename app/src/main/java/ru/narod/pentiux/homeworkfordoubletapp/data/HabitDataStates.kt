package ru.narod.pentiux.homeworkfordoubletapp.data

sealed class HabitDataStates
class HabitSuccess(val message: String) : HabitDataStates()
class HabitError(val errorMessage: String) : HabitDataStates()