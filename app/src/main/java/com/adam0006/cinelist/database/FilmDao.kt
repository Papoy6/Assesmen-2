package com.adam0006.cinelist.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM film ORDER BY id DESC")
    fun getAllFilm(): Flow<List<Film>>

    @Query("SELECT * FROM film ORDER BY judul ASC")
    fun getAllFilmSortedByTitle(): Flow<List<Film>>

    @Query("SELECT * FROM film ORDER BY rating DESC")
    fun getAllFilmSortedByRating(): Flow<List<Film>>

    @Query("SELECT * FROM film ORDER BY tahun DESC")
    fun getAllFilmSortedByYear(): Flow<List<Film>>

    @Insert
    suspend fun insert(film: Film)

    @Query("SELECT * FROM film WHERE id = :id")
    fun getFilmById(id: Int): Flow<Film?>

    @Delete
    suspend fun delete(film: Film)

    @Update
    suspend fun update(film: Film)
}