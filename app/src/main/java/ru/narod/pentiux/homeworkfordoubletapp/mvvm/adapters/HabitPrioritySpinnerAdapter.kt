package ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import ru.narod.pentiux.homeworkfordoubletapp.R
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitPriority

class HabitPrioritySpinnerAdapter(
    private val habit: HabitCharacteristicsData,
    private val spinner: AppCompatSpinner,
    private val context: Context
    ) : AdapterView.OnItemSelectedListener{

    init {
        spinner.adapter = ArrayAdapter.createFromResource(
            context,
            R.array.habit_priority_list,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = it
        }
        spinner.setSelection(habit.priority.intValue)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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