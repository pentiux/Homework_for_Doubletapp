package ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitPriority

class HabitPrioritySpinnerAdapter(
    private val habit: HabitCharacteristicsData,
    spinner: AppCompatSpinner,
    private val context: Context
    ): AdapterView.OnItemSelectedListener {

    private var firstCall = true

    init {
        spinner.setSelection(habit.priority.intValue)
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(firstCall) {
            firstCall = false
            return
        }
        for (priority in HabitPriority.values()) {
            if (priority.intValue == position) {
                Toast.makeText(context, "Priority of ${habit.name} changed on $priority", Toast.LENGTH_SHORT)
                    .show()
                habit.priority = priority
                break
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}