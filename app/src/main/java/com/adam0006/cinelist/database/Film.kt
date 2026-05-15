package com.adam0006.cinelist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film")
data class Film(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val judul: String,
    val genre: String,
    val tahun: String,
    val sudahDitonton: Boolean = false,
    val rating: Float = 0f,
    val imageUri: String? = null,
    val isFavorite: Boolean = false
)