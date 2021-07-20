package ru.narod.pentiux.homeworkfordoubletapp.mvvm.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import ru.narod.pentiux.homeworkfordoubletapp.R
import ru.narod.pentiux.homeworkfordoubletapp.databinding.FragmentMainBinding
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.viewmodels.MainHabitsViewModel
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.adapters.HabitsListAdapter
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.data.HabitCharacteristicsData
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.utils.repeatOnLifecycle

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = checkNotNull(_binding) { "MainFragment _binding isn't initialized!" }

    private val viewModel: MainHabitsViewModel by activityViewModels()

    private lateinit var habitsListAdapter: HabitsListAdapter
    private var _recyclerView: RecyclerView? = null
    private val recyclerView get() = checkNotNull(_recyclerView) { "MainFragment _recyclerView isn't initialized!" }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        habitsListAdapter = HabitsListAdapter { goToHabitEditor(it) }

       viewModel.resultList.onEach {
           habitsListAdapter.submitList(it)
       }.repeatOnLifecycle(viewLifecycleOwner)

        _recyclerView = binding.recyclerViewMainFragment.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = habitsListAdapter
        }

        binding.includeBottomSheet.fabCreateNewHabit.setOnClickListener {
            goToHabitEditor(HabitCharacteristicsData.getEmptyHabit())
        }
        setClickListeners()
    }

    private fun setClickListeners() {
        with(binding.includeBottomSheet) {
            sortByName.setOnClickListener {
                viewModel.flags["sortByName"] = sortByName.isChecked
                viewModel.updateHabitsList()
            }
            sortByType.setOnClickListener {
                viewModel.flags["sortByType"] = sortByType.isChecked
                viewModel.updateHabitsList()
            }
            sortByPriority.setOnClickListener {
                viewModel.flags["sortByPriority"]  = sortByPriority.isChecked
                viewModel.updateHabitsList()
            }
            sortByColor.setOnClickListener {
                viewModel.flags["sortByColor"]  = sortByColor.isChecked
                viewModel.updateHabitsList()
            }
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