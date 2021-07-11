package ru.narod.pentiux.homeworkfordoubletapp.mvvm.model

import kotlinx.coroutines.flow.Flow
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData

interface HabitModel {
    fun getAllHabits(): Flow<List<HabitCharacteristicsData>>

    fun deleteHabit(habit: HabitCharacteristicsData): ModelState

    fun insertHabit(habit: HabitCharacteristicsData): ModelState

    fun updateHabit(habit: HabitCharacteristicsData): ModelState

    fun getHabit(name: String): ModelState
}