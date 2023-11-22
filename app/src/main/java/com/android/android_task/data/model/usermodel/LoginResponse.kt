package com.android.android_task.data.model.usermodel

data class LoginResponse(
    val apiVersion: String,
    val oauth: Oauth,
    val permissions: List<String>,
    val showPasswordPrompt: Boolean,
    val userInfo: UserInfo
)