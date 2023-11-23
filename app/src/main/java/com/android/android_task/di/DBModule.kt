package com.android.android_task.di

import android.content.Context
import androidx.room.Room
import com.android.android_task.data.local.CharacterDAO
import com.android.android_task.data.local.LocalDataSource
import com.android.android_task.data.local.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun localDataSource(characterDao: CharacterDAO): LocalDataSource {
        return LocalDataSource(characterDao)
    }

    @Provides
    fun provideRoomDB(@ApplicationContext context: Context): RoomDB {
        return Room
            .databaseBuilder(context, RoomDB::class.java, "LocalDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTaskDao(roomDB: RoomDB):CharacterDAO {
        return roomDB.taskDao()
    }
}