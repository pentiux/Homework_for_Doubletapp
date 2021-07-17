package ru.narod.pentiux.homeworkfordoubletapp.data

import kotlinx.coroutines.flow.Flow
import ru.narod.pentiux.homeworkfordoubletapp.data.entities.HabitCharacteristicsEntity

interface DataManager {

    suspend fun insertHabit(habit: HabitCharacteristicsEntity) : HabitDataStates

    suspend fun updateHabit(habit: HabitCharacteristicsEntity) : HabitDataStates

    suspend fun deleteHabit(habit: HabitCharacteristicsEntity) : HabitDataStates

    fun getAllHabits(): Flow<List<HabitCharacteristicsEntity>>

}