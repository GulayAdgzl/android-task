package com.android.android_task.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.android_task.data.model.CharacterModel
import kotlinx.coroutines.launch

class HomeViewModel(
) : ViewModel() {
    private val _statusLiveData = MutableLiveData<List<CharacterModel>>()
    val statusLiveData: LiveData<List<CharacterModel>>
      get() = _statusLiveData

    init {
        authorizeAndFetchTask()
    }

    private fun authorizeAndFetchTask() {
        viewModelScope.launch {
            try {
                // Step 1: Authorize


                // Step 2: Fetch Tasks

            } catch (e: Exception) {

            }
        }
    }



}









