package ru.narod.pentiux.homeworkfordoubletapp.mvvm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.HabitModel
import javax.inject.Inject

@HiltViewModel
class MainHabitsViewModel @Inject constructor(
    private val habitModel: HabitModel
) : ViewModel() {

}