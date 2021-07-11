package ru.narod.pentiux.homeworkfordoubletapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.narod.pentiux.homeworkfordoubletapp.data.entities.HabitCharacteristicsEntity
import ru.narod.pentiux.homeworkfordoubletapp.data.dao.HabitsDao

@Database(
    entities = [HabitCharacteristicsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HabitsDatabase : RoomDatabase() {

    abstract fun getDao(): HabitsDao
}