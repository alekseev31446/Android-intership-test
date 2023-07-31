package com.example.myapplication2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Insert
    suspend fun insertAnimal(animal: Animal)

    @Insert
    suspend fun insertAnimals(animals: List<Animal>)

    @Query("SELECT * FROM animals")
    fun getAllAnimals(): LiveData<List<Animal>>

    @Query("SELECT * FROM animals WHERE isLiked = 1")
    fun getLikedAnimals(): LiveData<List<Animal>>

    @Update
    fun updateAnimal(animal: Animal)
}
