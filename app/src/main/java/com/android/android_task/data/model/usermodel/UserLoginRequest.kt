package com.android.android_task.data.model.usermodel

import com.google.gson.annotations.SerializedName

data class UserLoginRequest (
    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String
)