package ru.narod.pentiux.homeworkfordoubletapp.mvvm.model

import kotlinx.coroutines.flow.*
import ru.narod.pentiux.homeworkfordoubletapp.data.DataManager
import ru.narod.pentiux.homeworkfordoubletapp.data.HabitDataStateError
import ru.narod.pentiux.homeworkfordoubletapp.data.HabitDataStateSuccess
import ru.narod.pentiux.homeworkfordoubletapp.data.HabitDataStates
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.usecases.DataMapper
import ru.narod.pentiux.homeworkfordoubletapp.usecases.HabitListErrors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitModelImpl @Inject constructor(
    private val dbManager: DataManager,
    private val mapper: DataMapper
    ) : HabitModel {

    override fun getAllHabits(): Flow<List<HabitCharacteristicsData>> =
        dbManager.getAllHabits().map { it.map { habit -> mapper.fromDataForPresenter(habit) } }

    override suspend fun deleteHabit(habit: HabitCharacteristicsData): ModelState =
        convertState(dbManager.deleteHabit(mapper.fromPresenterForData(habit)))

    override suspend fun insertHabit(habit: HabitCharacteristicsData): ModelState =
        convertState(dbManager.insertHabit(mapper.fromPresenterForData(habit)))


    override suspend fun updateHabit(habit: HabitCharacteristicsData): ModelState =
        convertState(dbManager.updateHabit(mapper.fromPresenterForData(habit)))

    private fun convertState(dataState: HabitDataStates): ModelState =
         when (dataState) {
            is HabitDataStateSuccess -> ModelStateSuccess(dataState.message)
            is HabitDataStateError -> ModelStateError(dataState.errorMessage)
        }
}