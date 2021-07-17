package ru.narod.pentiux.homeworkfordoubletapp.data

import kotlinx.coroutines.flow.Flow
import ru.narod.pentiux.homeworkfordoubletapp.data.dao.HabitsDao
import ru.narod.pentiux.homeworkfordoubletapp.data.entities.HabitCharacteristicsEntity
import ru.narod.pentiux.homeworkfordoubletapp.usecases.HabitListErrors
import ru.narod.pentiux.homeworkfordoubletapp.usecases.HabitListSuccess
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerImp @Inject constructor(private val habitDao: HabitsDao) : DataManager {

    override suspend fun insertHabit(habit: HabitCharacteristicsEntity) =
        if (isNameUnique(habit.name)) {
            habitDao.insertHabit(habit)
            HabitDataStateSuccess(HabitListSuccess.INSERTED.message)
        } else HabitDataStateError(HabitListErrors.EXIST.message)

    override suspend fun updateHabit(habit: HabitCharacteristicsEntity) =
        if (isIdExist(habit.id)) {
            habitDao.updateHabit(habit)
            HabitDataStateSuccess(HabitListSuccess.UPDATED.message)
        } else HabitDataStateError(HabitListErrors.NO_SUCH_NAME.message)

    override suspend fun deleteHabit(habit: HabitCharacteristicsEntity)  =
        if (!isNameUnique(habit.name)) {
            habitDao.deleteHabit(habit)
            HabitDataStateSuccess(HabitListSuccess.DELETED.message)
        } else HabitDataStateError(HabitListErrors.EXIST.message)

    override fun getAllHabits(): Flow<List<HabitCharacteristicsEntity>> =
        habitDao.getAllHabits()

    private fun isNameUnique(name: String) = habitDao.checkNameInTable(name) == 0
    private fun isIdExist(id: Int) = habitDao.checkId(id) == 1
}