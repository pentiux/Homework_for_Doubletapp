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

    private var habitsList: Map<String, HabitCharacteristicsData> =  mapOf()

    override fun getAllHabits(): Flow<List<HabitCharacteristicsData>> =
        dbManager.getAllHabits().map { it.map { habit -> mapper.fromDataForPresenter(habit) } }.apply {
            map { habitsList = it.map { data -> data.name to data }.toMap() }
        }

    override fun getHabit(name: String): ModelState =
        if (checkName(name)) {
            val habit = habitsList[name]
            if (habit != null) {
                ModelStateData(habit)
            } else ModelStateError(HabitListErrors.NO_SUCH_NAME.message)
        } else ModelStateError(HabitListErrors.NO_SUCH_NAME.message)

    override suspend fun deleteHabit(habit: HabitCharacteristicsData): ModelState =
        if(checkName(habit.name)) convertState(dbManager.deleteHabit(mapper.fromPresenterForData(habit)))
        else ModelStateError(HabitListErrors.NO_SUCH_NAME.message)

    override suspend fun insertHabit(habit: HabitCharacteristicsData): ModelState =
        if (!checkName(habit.name)) convertState(dbManager.insertHabit(mapper.fromPresenterForData(habit)))
        else ModelStateError(HabitListErrors.NO_SUCH_NAME.message)

    override suspend fun updateHabit(habit: HabitCharacteristicsData): ModelState =
        if (checkName(habit.name)) convertState(dbManager.updateHabit(mapper.fromPresenterForData(habit)))
        else ModelStateError(HabitListErrors.NO_SUCH_NAME.message)

    private fun checkName(name: String): Boolean = habitsList.containsKey(name)

    private fun convertState(dataState: HabitDataStates): ModelState =
        when (dataState) {
            is HabitDataStateSuccess -> ModelStateSuccess(dataState.message)
            is HabitDataStateError -> ModelStateError(dataState.errorMessage)
        }
}