package com.android.android_task.ui.home

import com.android.android_task.data.Repository
import com.android.android_task.data.model.CharacterModel
import com.android.android_task.util.Resource
import com.android.android_task.util.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


const val AUTH_BEARER = "Bearer "

class UseCase @Inject constructor (private val repository: Repository,
                                   private val tokenManager: TokenManager
) {
    operator fun invoke(): Flow<Resource<List<CharacterModel>>> = flow {
        try {
            emit(Resource.loading())
            val authToken = AUTH_BEARER + tokenManager.getAccessToken()
            val tasks = repository.getItems(authToken)
            emit(Resource.success(tasks))
        } catch (e: HttpException) {
            emit(Resource.error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.error("Check your internet connection"))
        }
    }

}