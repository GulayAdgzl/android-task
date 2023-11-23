package com.android.android_task.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.android_task.data.Repository
import com.android.android_task.ui.home.HomeViewModel
import com.android.android_task.ui.home.UseCase
import com.android.android_task.util.Resource
import com.android.android_task.util.TokenManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@HiltWorker
class RefreshWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: Repository,
    private val tokenManager: TokenManager,
    private val taskUseCase: UseCase
): CoroutineWorker(context,workerParameters) {

    companion object {
        const val WORK_NAME = "refresh_data_work"
    }

    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            try {
                authorizeAndFetchTask()
                Log.i("sync_items", "WorkManager: Work request for sync is run ")
            } catch (e: Exception) {
                Log.e("sync_items", "doWork: ${e.message}")
                e.printStackTrace()
                return@withContext Result.retry()
            }
        }
        return Result.success()
    }

    private suspend fun authorizeAndFetchTask() {

        try {
            // authorize
            val response = repository.authorize()
            val token = response.oauth.access_token
            tokenManager.saveAccessToken(token)
            fetchTask()
        } catch (e: Exception) {
            if (e is HttpException) {
                e.message?.let {
                    Log.e(Repository::class.simpleName, it)
                }
            } else {
                e.message?.let { Log.e(Repository::class.simpleName, it) }
                e.printStackTrace()
            }
        }

    }

    private fun fetchTask() {
        try {
            taskUseCase().onEach{ result ->
                when (result.status) {
                    Resource.Status.SUCCESS -> {
                        result.data?.let { repository.saveData(it) }
                    }
                    Resource.Status.ERROR -> {
                        Log.e(
                            HomeViewModel::class.simpleName,
                            result.message ?: "Unknown error occurred"
                        )
                    }
                    Resource.Status.LOADING -> {
                    }
                }
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                e.message?.let {
                    Log.e(Repository::class.simpleName, it)
                }
            } else {
                e.message?.let { Log.e(Repository::class.simpleName, it) }
                e.printStackTrace()
            }
        }
    }
}