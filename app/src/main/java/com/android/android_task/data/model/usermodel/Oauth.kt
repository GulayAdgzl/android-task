package com.android.android_task.data.model.usermodel

data class Oauth(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: Any,
    val token_type: String
)
