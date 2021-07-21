package ru.narod.pentiux.homeworkfordoubletapp.mvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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

    private val _resultList: MutableStateFlow<List<HabitCharacteristicsData>> = MutableStateFlow(listOf())
    val resultList: StateFlow<List<HabitCharacteristicsData>> = _resultList.asStateFlow()

    private var beforeSortAndSearch = listOf<HabitCharacteristicsData>()
    private var searchString = ""
    private var searchJob: Job? = null

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
                updateCurrentHabitsList(it)
            }
        }
    }

    fun searchByName(string: String) {
        searchString = string
        val timer = 200L
        if (searchJob == null) {
            searchJob = viewModelScope.launch(ioDispatcher) {
                delay(timer)
                val tempList = beforeSortAndSearch.filter { it.name.contains(searchString, ignoreCase = true) }
                updateCurrentHabitsList(tempList)
            }
            searchJob = null
        }
    }

    fun updateCurrentHabitsList(list: List<HabitCharacteristicsData> = beforeSortAndSearch) {
        if (flags.containsValue(true)) _resultList.value = sortList(list)
        else _resultList.value = list
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

    private fun sortList(list: List<HabitCharacteristicsData>): List<HabitCharacteristicsData> =
        list.sortedWith(
            compareBy (
                { habit -> if (flags["sortByName"]!!) habit.name else 0 },
                { habit -> if (flags["sortByType"]!!) habit.type else 0 },
                { habit -> if (flags["sortByPriority"]!!) habit.priority else 0 },
                { habit -> if (flags["sortByColor"]!!) habit.color else 0 })
        )

    private fun getAllHabits(): Flow<List<HabitCharacteristicsData>> = habitModel.getAllHabits()
}