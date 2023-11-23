package com.android.android_task.data.local

import com.android.android_task.data.model.CharacterModel
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val characterDao: CharacterDAO
) {
    fun getLocal(): List<CharacterModel>{
        return characterDao.getLocal()
    }

    suspend fun saveData(model: List<CharacterModel>){
        characterDao.saveDao(model)
    }
}