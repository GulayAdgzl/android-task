package com.android.android_task.data.model

import com.google.gson.annotations.SerializedName

data class CharacterModel (
    @SerializedName("BusinessUnitKey")
    val businessUnitKey: String?,
    @SerializedName("businessUnit")
    val businessUnit: String?,
    @SerializedName("colorCode")
     val colorCode: String?,
    @SerializedName("description")
     val description: String?,
    @SerializedName("isAvailableInTimeTrackingKioskMode")
     val isAvailableInTimeTrackingKioskMode: Boolean,
    @SerializedName("parentTaskID")
     val parentTaskId: String?,
    @SerializedName("sort")
    val sort: String?,
    @SerializedName("task")
     val task: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("wageType")
    val wageType: String?,
        )