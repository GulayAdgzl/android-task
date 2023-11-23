package com.android.android_task.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.android_task.data.Repository
import com.android.android_task.data.model.CharacterModel
import com.android.android_task.util.Resource
import com.android.android_task.util.TokenManager
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(
    private val useCase: UseCase,
    private val tokenManager: TokenManager,
    private val repository: Repository
) : ViewModel() {
    private val _characterLiveData = MutableLiveData<Resource<List<CharacterModel>>>()
    val characterLiveData: LiveData<Resource<List<CharacterModel>>> = _characterLiveData

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
                fetchTasks()
            } catch (e: Exception) {
                handleAuthorizationError(e)
            }
        }
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            try {
                useCase().collect { result ->
                    when (result.status) {
                        Resource.Status.SUCCESS -> {
                            _characterLiveData.value = result
                            result.data?.let { repository.saveData(it) }
                        }
                        Resource.Status.ERROR -> handleTaskFetchError(result.message)
                        Resource.Status.LOADING -> {
                            // Do nothing for now
                        }
                    }
                }
            } catch (e: Exception) {
                handleTaskFetchError(e.localizedMessage)
            }
        }
    }

    fun getLocalTasks(): List<CharacterModel> {
        return repository.getData()
    }

    private fun handleAuthorizationError(exception: Exception) {
        if (exception is HttpException) {
            exception.message?.let {
                Log.e(Repository::class.simpleName, it)
            }
        } else {
            exception.message?.let {
                Log.e(Repository::class.simpleName, it)
            }
            exception.printStackTrace()
        }
    }

    private fun handleTaskFetchError(errorMessage: String?) {
        Log.e(
            HomeViewModel::class.simpleName,
            errorMessage ?: "Unknown error occurred during task fetch"
        )
    }
}






