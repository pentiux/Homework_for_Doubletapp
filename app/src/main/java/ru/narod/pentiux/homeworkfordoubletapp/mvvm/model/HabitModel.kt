package ru.narod.pentiux.homeworkfordoubletapp.mvvm.model

import kotlinx.coroutines.flow.Flow
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData

interface HabitModel {
    fun getAllHabits(): Flow<List<HabitCharacteristicsData>>

    suspend fun deleteHabit(habit: HabitCharacteristicsData): ModelState

    suspend fun insertHabit(habit: HabitCharacteristicsData): ModelState

    suspend fun updateHabit(habit: HabitCharacteristicsData): ModelState


}