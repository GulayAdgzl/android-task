package com.android.android_task.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.android_task.data.model.CharacterModel

@Database(entities = [CharacterModel::class], version = 1)
abstract class CharacterDB : RoomDatabase() {

    abstract fun taskDao() : CharacterDAO


    companion object{

        @Volatile private var instance : CharacterDB? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, CharacterDB::class.java,"taskdatabase"
        ).build()

    }
}