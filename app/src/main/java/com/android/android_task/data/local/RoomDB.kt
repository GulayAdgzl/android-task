package com.android.android_task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.android_task.data.model.CharacterModel

@Database(entities = [CharacterModel::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun taskDao(): CharacterDAO
}