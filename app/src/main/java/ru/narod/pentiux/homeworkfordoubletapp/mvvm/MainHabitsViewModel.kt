package ru.narod.pentiux.homeworkfordoubletapp.mvvm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import ru.narod.pentiux.homeworkfordoubletapp.di.coroutines.IoDispatcher
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.*
import ru.narod.pentiux.homeworkfordoubletapp.usecases.HabitListSuccess
import javax.inject.Inject

@HiltViewModel
class MainHabitsViewModel @Inject constructor(
    private val habitModel: HabitModel,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getAllHabits(): Flow<List<HabitCharacteristicsData>> = habitModel.getAllHabits()

    fun getHabit(name: String): ModelState = habitModel.getHabit(name)

    suspend fun deleteHabit(habit: HabitCharacteristicsData): ModelState = withContext(ioDispatcher) {
        habitModel.deleteHabit(habit)
    }

    suspend fun insertHabit(habit: HabitCharacteristicsData): ModelState = withContext(ioDispatcher) {
        habitModel.deleteHabit(habit)
    }
    suspend fun updateHabit(habit: HabitCharacteristicsData): ModelState = withContext(ioDispatcher) {
        habitModel.deleteHabit(habit)
    }

    fun convertStateForTranslation(state: ModelState): String =
        when(state) {
            is ModelStateError -> if (HabitListSuccess.values().any { it.message == state.errorMessage }) {
                state.errorMessage
            } else "Fix error enum"
            is ModelStateSuccess -> if (HabitListSuccess.values().any { it.message == state.message }) {
                state.message
            } else "Fix success enum"
            is ModelStateData -> ""
        }
}