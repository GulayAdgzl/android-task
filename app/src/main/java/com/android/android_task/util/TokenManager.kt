package com.android.android_task.util

import android.content.Context
import com.android.android_task.R

class TokenManager(context: Context)  {

    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )
}