package com.example.myapplication1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users ORDER BY name ASC")
    fun sortByName(): Flow<List<User>>

    @Query("SELECT * FROM users ORDER BY age ASC")
    fun sortByAge(): Flow<List<User>>

    @Query("SELECT * FROM users ORDER BY isStudent DESC")
    fun sortByStatus(): Flow<List<User>>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}