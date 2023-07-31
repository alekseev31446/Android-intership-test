package com.example.myapplication1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo("name")
    var name : String,
    @ColumnInfo("age")
    var age : Int,
    @ColumnInfo("isStudent")
    var isStudent : Boolean = false
)