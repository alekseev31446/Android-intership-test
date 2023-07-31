package com.example.myapplication2

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimalRepository(application: Application) {
    private val animalDao: Dao

    init {
        val db = MainDb.getDb(application)
        animalDao = db.getDao()
    }

    fun getAllAnimals(): LiveData<List<Animal>> {
        return animalDao.getAllAnimals()
    }

    suspend fun insertAnimals(animals: List<Animal>) {
        withContext(Dispatchers.IO) {
            animalDao.insertAnimals(animals)
        }
    }
}

