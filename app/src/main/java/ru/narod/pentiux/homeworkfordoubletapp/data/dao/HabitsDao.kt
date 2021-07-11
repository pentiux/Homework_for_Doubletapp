package ru.narod.pentiux.homeworkfordoubletapp.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.narod.pentiux.homeworkfordoubletapp.data.entities.HabitCharacteristicsEntity

@Dao
interface HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitCharacteristicsEntity)

    @Update
    suspend fun updateHabit(habit: HabitCharacteristicsEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitCharacteristicsEntity)

    @Query("SELECT * FROM habit_characteristics WHERE name = :name")
    fun getHabit(name: String): Flow<HabitCharacteristicsEntity>

    @Query("SELECT * FROM habit_characteristics")
    fun getAllHabits(): Flow<List<HabitCharacteristicsEntity>>
}