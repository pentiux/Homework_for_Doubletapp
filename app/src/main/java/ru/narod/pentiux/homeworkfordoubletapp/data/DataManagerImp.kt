package ru.narod.pentiux.homeworkfordoubletapp.data

import kotlinx.coroutines.flow.Flow
import ru.narod.pentiux.homeworkfordoubletapp.data.dao.HabitsDao
import ru.narod.pentiux.homeworkfordoubletapp.data.entities.HabitCharacteristicsEntity
import javax.inject.Inject

class DataManagerImp @Inject constructor(private val habitDao: HabitsDao) : DataManager {

    override suspend fun insertHabit(habit: HabitCharacteristicsEntity) =
        if (isNameUnique(habit.name)) {
            habitDao.insertHabit(habit)
            HabitSuccess("Habit was successfully inserted")
        } else HabitError("Habit is already exist")

    override suspend fun updateHabit(habit: HabitCharacteristicsEntity) =
        if (isNameUnique(habit.name)) {
            habitDao.updateHabit(habit)
            HabitSuccess("Habit was successfully updated.")
        } else HabitError("No such habit name.")

    override suspend fun deleteHabit(habit: HabitCharacteristicsEntity)  =
        if (isNameUnique(habit.name)) {
            habitDao.deleteHabit(habit)
            HabitSuccess("Habit was successfully deleted")
        } else HabitError("No such habit name.")

    override fun getAllHabits(): Flow<List<HabitCharacteristicsEntity>> =
        habitDao.getAllHabits()

    private fun isNameUnique(name: String) = habitDao.checkNameInTable(name) == 0
}