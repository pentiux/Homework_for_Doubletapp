package ru.narod.pentiux.homeworkfordoubletapp.mvvm.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.narod.pentiux.homeworkfordoubletapp.R
import ru.narod.pentiux.homeworkfordoubletapp.databinding.FragmentColorEditBinding
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.viewmodels.EditViewModel


class EditColorFragment : Fragment(R.layout.fragment_color_edit) {

    private var _binding: FragmentColorEditBinding? = null
    private val binding get() = checkNotNull(_binding) { "EditColorFragment: _binding isn't initialized!" }

    private val args: EditColorFragmentArgs by navArgs()
    private val viewModel: EditViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentColorEditBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        binding.currentColor.setBackgroundColor(args.color)
        viewModel.tempColor = args.color
        setViewBackground()
        colorPicker()
        buttonsSetListeners()
    }

    private fun buttonsSetListeners() {
        val navController = findNavController()
        binding.colorApply.setOnClickListener {
            viewModel.editorHabit.color = viewModel.tempColor
            navController.navigateUp()
        }
        binding.colorCancel.setOnClickListener { navController.navigateUp() }
    }

    private fun colorPicker(){
        var startTime = 0L
        binding.view1.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                startTime = event.eventTime
            } else if (event.action == MotionEvent.ACTION_UP) {
                if (event.eventTime - startTime < 150) {
                    val color = v.background.toBitmap(v.width, v.height).getPixel(event.x.toInt(), event.y.toInt())
                    binding.currentColor.setBackgroundColor(color)
                    viewModel.tempColor = color
                }
            }
            v.performClick()
        }
    }

    private fun setViewBackground() {
        val rainbow = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(Color.BLACK, Color.WHITE, Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED)
        )
        binding.view1.background = rainbow
    }
}