package ru.narod.pentiux.homeworkfordoubletapp.mvvm.utils

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


fun <T> Flow<T>.repeatOnLifecycle(viewLifecycleOwner: LifecycleOwner) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@repeatOnLifecycle.collect()
        }
    }
}
