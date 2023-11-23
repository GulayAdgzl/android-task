package com.android.android_task.data

import com.android.android_task.data.local.LocalDataSource
import com.android.android_task.data.model.CharacterModel
import com.android.android_task.data.model.usermodel.LoginResponse
import com.android.android_task.data.remote.CustomRemoteDataSource

class Repository(
    private val remote: CustomRemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun authorize(): LoginResponse {
        return remote.authenticate()

    }
    suspend fun getItems(authToken:String) = remote.fetchItems(authToken)

    fun getData()= localDataSource.getLocal()

    suspend fun saveData(model : List<CharacterModel>) = localDataSource.saveData(model)

}