package ru.narod.pentiux.homeworkfordoubletapp.usecases

import ru.narod.pentiux.homeworkfordoubletapp.data.entities.HabitCharacteristicsEntity
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitPriority
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataMapper @Inject constructor() {

    fun fromDataForPresenter(habitEntity: HabitCharacteristicsEntity) =
        HabitCharacteristicsData(
            id = habitEntity.id,
            name = habitEntity.name,
            description = habitEntity.description,
            type = habitEntity.type,
            frequency = habitEntity.frequency,
            priority = HabitPriority.values()[habitEntity.priority],
            color = habitEntity.color
        )

    fun fromPresenterForData(habitData: HabitCharacteristicsData) =
        HabitCharacteristicsEntity(
            id = habitData.id,
            name = habitData.name,
            description = habitData.description,
            type = habitData.type,
            frequency = habitData.frequency,
            priority = habitData.priority.intValue,
            color = habitData.color
        )
}