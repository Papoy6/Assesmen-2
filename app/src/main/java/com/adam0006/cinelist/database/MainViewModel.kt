package com.adam0006.cinelist.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adam0006.cinelist.database.Film
import com.adam0006.cinelist.database.FilmDao
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private var filmTerakhirDihapus: Film? = null
    
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow

    fun tambahFilm(judul: String, genre: String, tahun: String, sudahDitonton: Boolean) {
        viewModelScope.launch {
            val filmBaru = Film(judul = judul, genre = genre, tahun = tahun, sudahDitonton = sudahDitonton)
            dao.insert(filmBaru)
        }
    }

    suspend fun getFilmById(id: Int): Film? {
        return dao.getFilmById(id)
    }

    fun hapusFilm(film: Film) {
        viewModelScope.launch {
            filmTerakhirDihapus = film
            dao.delete(film)
            _eventFlow.emit(UiEvent.ShowUndoSnackbar("Film berhasil dihapus"))
        }
    }

    fun undoHapus() {
        viewModelScope.launch {
            filmTerakhirDihapus?.let {
                dao.insert(it)
                filmTerakhirDihapus = null
            }
        }
    }

    fun editFilm(film: Film) {
        viewModelScope.launch {
            dao.update(film)
        }
    }

    sealed class UiEvent {
        data class ShowUndoSnackbar(val message: String) : UiEvent()
    }
}