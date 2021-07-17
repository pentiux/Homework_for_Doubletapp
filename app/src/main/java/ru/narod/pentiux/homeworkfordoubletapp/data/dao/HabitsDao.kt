package ru.narod.pentiux.homeworkfordoubletapp.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.narod.pentiux.homeworkfordoubletapp.data.entities.HabitCharacteristicsEntity

@Dao
interface HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitCharacteristicsEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHabit(habit: HabitCharacteristicsEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitCharacteristicsEntity)

    @Query("SELECT COUNT(name) FROM habit_characteristics WHERE name = :name")
    fun checkNameInTable(name: String): Int

    @Query("SELECT COUNT(id) FROM habit_characteristics WHERE id = :id")
    fun checkId(id: Int): Int

    @Query("SELECT * FROM habit_characteristics")
    fun getAllHabits(): Flow<List<HabitCharacteristicsEntity>>
}