package ru.narod.pentiux.homeworkfordoubletapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_characteristics")
data class HabitCharacteristicsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "priority") var priority: Int,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "frequency") var frequency: String,
    @ColumnInfo(name = "color") var color: Int
)
