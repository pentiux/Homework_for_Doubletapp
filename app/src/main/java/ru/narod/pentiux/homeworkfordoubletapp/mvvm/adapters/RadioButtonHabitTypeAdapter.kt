package ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters

import android.content.Context
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.children
import ru.narod.pentiux.homeworkfordoubletapp.R
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData

class RadioButtonHabitTypeAdapter(
    radioGroup: RadioGroup,
    private val habit: HabitCharacteristicsData,
    private val context: Context
    ) {

    init {
        if (!isTypeEmptyOrWrong()) habit.type = context.resources.getStringArray(R.array.habit_type_list)[0]

        for (view in radioGroup.children) {
            val radioButton = (view as? com.google.android.material.radiobutton.MaterialRadioButton) ?:
                throw Exception("RadioButtonHabitTypeAdapter, init, radioButton: Wrong view!")

            if (radioButton.text == habit.type) {
                radioButton.isChecked = true
            }

            radioButton.setOnClickListener { radioClickListener(radioButton) }
        }
    }

    private fun isTypeEmptyOrWrong() =
        context.resources.getStringArray(R.array.habit_type_list).contains(habit.type)

    private fun radioClickListener(radioButton: RadioButton) { // TODO сделать запись в базу данных
            Toast.makeText(context,
                "Type of ${habit.name} habit changed on ${radioButton.text}!",
                Toast.LENGTH_SHORT).show()
            habit.type = radioButton.text.toString()
    }
}