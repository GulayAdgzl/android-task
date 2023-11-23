package com.android.android_task.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.android_task.data.Repository
import com.android.android_task.data.model.CharacterModel
import com.android.android_task.util.TokenManager
import kotlinx.coroutines.launch

class HomeViewModel(
    private val useCase: UseCase,
    private val tokenManager: TokenManager,
    private val repository: Repository
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
                val authorizationResponse = repository.authorize()
                val accessToken = authorizationResponse.oauth.access_token
                tokenManager.saveAccessToken(accessToken)

                // Step 2: Fetch Tasks

            } catch (e: Exception) {

            }
        }
    }



}









