package ru.narod.pentiux.homeworkfordoubletapp.mvvm.model

import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData

sealed class ModelState
class ModelStateSuccess(val message: String) : ModelState()
class ModelStateError(val errorMessage: String) : ModelState()
class ModelStateData(val habit: HabitCharacteristicsData) : ModelState()
