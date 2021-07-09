package ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.narod.pentiux.homeworkfordoubletapp.databinding.HabitLayoutBinding
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData

class HabitsListAdapter(
    private val onItemClicked: (HabitCharacteristicsData) -> Unit
): ListAdapter<HabitCharacteristicsData, HabitsListAdapter.HabitsListViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<HabitCharacteristicsData>() {
        override fun areItemsTheSame(
            oldItem: HabitCharacteristicsData,
            newItem: HabitCharacteristicsData
        ) = oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: HabitCharacteristicsData,
            newItem: HabitCharacteristicsData
        ) = oldItem == newItem
    }

    class HabitsListViewHolder(private val binding: HabitLayoutBinding  )
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: HabitCharacteristicsData) = binding.apply {
            habitName.text = habit.name
            habitDescription.text = habit.description
            prioritySpinner.onItemSelectedListener = HabitPrioritySpinnerAdapter(habit, prioritySpinner, root.context)
            RadioButtonHabitTypeAdapter(habitLayoutRadioGroup, habit, root.context)
            habitatFrequency.text = habit.frequency
            habitColor.setBackgroundColor(habit.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsListViewHolder {
        val viewHolder = HabitsListViewHolder(
            HabitLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            onItemClicked(getItem(viewHolder.adapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: HabitsListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}