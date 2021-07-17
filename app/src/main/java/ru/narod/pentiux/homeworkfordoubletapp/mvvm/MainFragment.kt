package ru.narod.pentiux.homeworkfordoubletapp.mvvm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.onEach
import ru.narod.pentiux.homeworkfordoubletapp.R
import ru.narod.pentiux.homeworkfordoubletapp.databinding.FragmentMainBinding
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.HabitsListAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.utils.repeatOnLifecycle

class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = checkNotNull(_binding) { "MainFragment _binding isn't initialized!" }

    private val viewModel: MainHabitsViewModel by activityViewModels()

    private var _recyclerView: RecyclerView? = null
    private val recyclerView get() = checkNotNull(_recyclerView) { "MainFragment _recyclerView isn't initialized!" }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        val habitsListAdapter = HabitsListAdapter { goToHabitEditor(it) }

       viewModel.getAllHabits().onEach {
           habitsListAdapter.submitList(it)
       }.repeatOnLifecycle(viewLifecycleOwner)

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