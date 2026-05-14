package com.adam0006.miniproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film")
data class Film(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val judul: String,
    val genre: String,
    val tahun: String
)