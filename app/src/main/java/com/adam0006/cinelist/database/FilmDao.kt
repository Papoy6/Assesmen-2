package com.adam0006.cinelist.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM film ORDER BY id DESC")
    fun getAllFilm(): Flow<List<Film>>

    @Insert
    suspend fun insert(film: Film)

    @Query("SELECT * FROM film WHERE id = :id")
    suspend fun getFilmById(id: Int): Film?

    @Delete
    suspend fun delete(film: Film)

    @Update
    suspend fun update(film: Film)
}