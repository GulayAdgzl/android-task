package com.android.android_task.data.local

import com.android.android_task.data.model.CharacterModel

class LocalDataSource(
    private val characterDao: CharacterDAO
) {
    fun getLocal(): List<CharacterModel>{
        return characterDao.getLocal()
    }

    suspend fun saveData(model: List<CharacterModel>){
        characterDao.saveDao(model)
    }
}