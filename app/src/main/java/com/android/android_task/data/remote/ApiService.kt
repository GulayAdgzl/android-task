package com.android.android_task.data.remote

import com.android.android_task.data.model.CharacterModel
import com.android.android_task.data.model.usermodel.LoginResponse
import com.android.android_task.data.model.usermodel.UserLoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


const val LOGIN_URL = "index.php/login"
const val RESOURCES_URL = "dev/index.php/v1/tasks/select"
const val GIVEN_AUTH_HEADER  = "Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz"
const val CREDENTIAL_USERNAME = "365"
const val CREDENTIAL_PASSWORD= "1"

class CustomRemoteDataSource private constructor(
    private val apiService: ApiService
) {
    class Builder(private val apiService: ApiService) {
        fun build(): CustomRemoteDataSource {
            return CustomRemoteDataSource(apiService)
        }
    }


    suspend fun authenticate(): LoginResponse {
        val body = UserLoginRequest(CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD)
        return apiService.login(GIVEN_AUTH_HEADER, body)
    }

    suspend fun fetchItems(authToken: String): List<CharacterModel> {
        return apiService.getItems(authToken)
    }

}


interface ApiService {

    @POST(LOGIN_URL)
    suspend fun login(
        @Header("Authorization") authHeader: String,
        @Body request: UserLoginRequest
    ): LoginResponse

    @GET(RESOURCES_URL)
    suspend fun getItems(@Header("Authorization") token: String): List<CharacterModel>
}