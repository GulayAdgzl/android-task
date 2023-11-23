package com.android.android_task.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.android_task.data.model.CharacterModel

@Dao
interface CharacterDAO {

    @Query("SELECT * FROM taskModel")
    fun getLocal():List<CharacterModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDao(model:List<CharacterModel>)
}