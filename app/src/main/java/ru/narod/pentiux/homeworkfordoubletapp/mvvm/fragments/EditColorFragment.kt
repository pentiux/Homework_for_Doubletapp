package ru.narod.pentiux.homeworkfordoubletapp.mvvm.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.narod.pentiux.homeworkfordoubletapp.R
import ru.narod.pentiux.homeworkfordoubletapp.databinding.FragmentColorEditBinding

class EditColorFragment : Fragment(R.layout.fragment_color_edit) {

    private var _binding: FragmentColorEditBinding? = null
    private val binding get() = checkNotNull(_binding) { "EditColorFragment: _binding isn't initialized!" }

    private val args: EditColorFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentColorEditBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}