package ru.narod.pentiux.homeworkfordoubletapp.mvvm.model

import kotlinx.coroutines.flow.Flow
import ru.narod.pentiux.homeworkfordoubletapp.data.DataManager
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.usecases.DataMapper
import javax.inject.Inject

class HabitModelImpl @Inject constructor(
    private val dbManager: DataManager,
    private val mapper: DataMapper = DataMapper()
    ) : HabitModel {
    override fun getAllHabits(): Flow<List<HabitCharacteristicsData>> {
        TODO("Not yet implemented")
    }

    override fun deleteHabit(habit: HabitCharacteristicsData): ModelState {
        TODO("Not yet implemented")
    }

    override fun insertHabit(habit: HabitCharacteristicsData): ModelState {
        TODO("Not yet implemented")
    }

    override fun updateHabit(habit: HabitCharacteristicsData): ModelState {
        TODO("Not yet implemented")
    }

    override fun getHabit(name: String): ModelState {
        TODO("Not yet implemented")
    }

}