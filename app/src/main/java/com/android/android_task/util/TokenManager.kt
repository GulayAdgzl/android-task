package com.android.android_task.util

import android.content.Context
import com.android.android_task.R
import javax.inject.Inject

class TokenManager @Inject constructor(context: Context)  {

    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )
    fun saveAccessToken(accessToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString("ACCESS_TOKEN", accessToken)
        editor.apply()
    }
    fun getAccessToken(): String? {
        return sharedPreferences.getString("ACCESS_TOKEN", null)
    }
}