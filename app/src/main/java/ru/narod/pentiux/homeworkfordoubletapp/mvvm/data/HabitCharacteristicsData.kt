package ru.narod.pentiux.homeworkfordoubletapp.mvvm.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class HabitCharacteristicsData(
    var name: String,
    var description: String,
    var priority: HabitPriority,
    var type: String,
    var frequency: String,
    var color: Int
) : Parcelable {

    fun isDataEmptyOrBlank() =
        name.isBlank() && description.isBlank() && type.isBlank() && frequency.isBlank()

    companion object {
        fun getEmptyHabit() = HabitCharacteristicsData(
            "", "", HabitPriority.NORMAL, "", "", 0
        )
    }
}
