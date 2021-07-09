package ru.narod.pentiux.homeworkfordoubletapp.mvvm.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
enum class HabitPriority(val intValue: Int) : Parcelable {
    MINIMAL(0), LOW(1), NORMAL(2), HIGH(3), SUPERIOR(4)
}