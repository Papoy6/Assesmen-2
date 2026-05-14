package com.adam0006.cinelist.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adam0006.cinelist.database.Film
import com.adam0006.cinelist.database.FilmDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val dao: FilmDao) : ViewModel() {

    val dataFilm: StateFlow<List<Film>> = dao.getAllFilm()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun tambahFilm(judul: String, genre: String, tahun: String) {
        viewModelScope.launch {
            val filmBaru = Film(judul = judul, genre = genre, tahun = tahun)
            dao.insert(filmBaru)
        }
    }

    suspend fun getFilmById(id: Int): Film? {
        return dao.getFilmById(id)
    }

    fun hapusFilm(film: Film) {
        viewModelScope.launch {
            dao.delete(film)
        }
    }

    fun editFilm(film: Film) {
        viewModelScope.launch {
            dao.update(film)
        }
    }
}