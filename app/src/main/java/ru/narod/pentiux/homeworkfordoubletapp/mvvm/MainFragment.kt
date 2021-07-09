package ru.narod.pentiux.homeworkfordoubletapp.mvvm

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.narod.pentiux.homeworkfordoubletapp.R
import ru.narod.pentiux.homeworkfordoubletapp.databinding.FragmentMainBinding
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.HabitsListAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitPriority

internal class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = checkNotNull(_binding) { "MainFragment _binding isn't initialized!" }

    private val viewModel: MainHabitsViewModel by activityViewModels()

    private var _recyclerView: RecyclerView? = null
    private val recyclerView get() = checkNotNull(_recyclerView) { "MainFragment _recyclerView isn't initialized!" }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        val habitsListAdapter = HabitsListAdapter { goToHabitEditor(it) }.apply {
            submitList(listOf(
                HabitCharacteristicsData(
                    name = "Привычка 1",
                    description = "Описание привычки должно быть тут",
                    priority = HabitPriority.NORMAL,
                    type = "Type 1",
                    frequency = "Раз в день",
                    color = Color.BLACK
                ),
                HabitCharacteristicsData(
                    name = "Привычка 2",
                    description = "Описание привычки должно быть тут",
                    priority = HabitPriority.HIGH,
                    type = "Type 2",
                    frequency = "Раз в час",
                    color = Color.RED
                )
            )) //TODO добавить загрузку данных из БД
        }
        _recyclerView = binding.recyclerViewMainFragment.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = habitsListAdapter
        }

        binding.fabCreateNewHabit.setOnClickListener {
            goToHabitEditor(HabitCharacteristicsData.getEmptyHabit())
        }
    }

    private fun goToHabitEditor(habit: HabitCharacteristicsData) {
        val action = MainFragmentDirections.actionMainFragmentToHabitEditorScreen(habit)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _recyclerView = null
        _binding = null
    }
}