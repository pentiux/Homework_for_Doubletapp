package ru.narod.pentiux.homeworkfordoubletapp.mvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.narod.pentiux.homeworkfordoubletapp.di.coroutines.IoDispatcher
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.*
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.utils.repeatOnLifecycle
import ru.narod.pentiux.homeworkfordoubletapp.usecases.HabitListSuccess
import javax.inject.Inject

@HiltViewModel
class MainHabitsViewModel @Inject constructor(
    private val habitModel: HabitModel,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _resultList = MutableStateFlow<List<HabitCharacteristicsData>>(listOf())
    val resultList = _resultList.asStateFlow()

    private var beforeSortAndSearch = listOf<HabitCharacteristicsData>()

    val flags = mutableMapOf(
        "sortByName" to false,
        "sortByType" to false,
        "sortByPriority" to false,
        "sortByColor" to false
    )

    init {
        viewModelScope.launch(ioDispatcher) {
            getAllHabits().collect {
                beforeSortAndSearch = it
                _resultList.value = sortList(it)
            }
        }
    }

    fun updateHabitsList(){
        _resultList.value = sortList(_resultList.value)
    }

    suspend fun deleteHabit(habit: HabitCharacteristicsData): ModelState = withContext(ioDispatcher) {
        habitModel.deleteHabit(habit)
    }

    suspend fun insertHabit(habit: HabitCharacteristicsData): ModelState = withContext(ioDispatcher) {
        habitModel.insertHabit(habit)
    }
    suspend fun updateHabit(habit: HabitCharacteristicsData): ModelState = withContext(ioDispatcher) {
        habitModel.updateHabit(habit)
    }

    private fun sortList(list: List<HabitCharacteristicsData>): List<HabitCharacteristicsData> {
        return if (flags.containsValue(true))
        list.sortedWith(
            compareBy (
                { habit -> if (flags["sortByName"]!!) habit.name else 0 },
                { habit -> if (flags["sortByType"]!!) habit.type else 0 },
                { habit -> if (flags["sortByPriority"]!!) habit.priority else 0 },
                { habit -> if (flags["sortByColor"]!!) habit.color else 0 })
        )
        else beforeSortAndSearch
    }

    private fun getAllHabits(): Flow<List<HabitCharacteristicsData>> = habitModel.getAllHabits()
}