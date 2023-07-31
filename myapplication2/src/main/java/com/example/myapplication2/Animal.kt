package com.example.myapplication2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class Animal(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo("imageFilePath")
    var imageFilePath : String,
    @ColumnInfo("isLiked")
    var isLiked : Boolean = false
)