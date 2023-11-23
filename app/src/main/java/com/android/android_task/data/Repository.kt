package com.android.android_task.data

import com.android.android_task.data.model.usermodel.LoginResponse
import com.android.android_task.data.remote.CustomRemoteDataSource

class Repository(
    private val remote: CustomRemoteDataSource
) {
    suspend fun authorize(): LoginResponse {
        return remote.authenticate()

    }
    suspend fun getItems(authToken:String) = remote.fetchItems(authToken)
}