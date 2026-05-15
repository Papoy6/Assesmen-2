package com.adam0006.cinelist.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val dao: FilmDao) : ViewModel() {

    private val _sortOrder = MutableStateFlow(SortOrder.ID_DESC)
    val sortOrder: StateFlow<SortOrder> = _sortOrder

    @OptIn(ExperimentalCoroutinesApi::class)
    val dataFilm: StateFlow<List<Film>> = _sortOrder.flatMapLatest { order ->
        when (order) {
            SortOrder.ID_DESC -> dao.getAllFilm()
            SortOrder.JUDUL_ASC -> dao.getAllFilmSortedByTitle()
            SortOrder.RATING_DESC -> dao.getAllFilmSortedByRating()
            SortOrder.TAHUN_DESC -> dao.getAllFilmSortedByYear()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private var filmTerakhirDihapus: Film? = null
    
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow

    fun changeSortOrder(order: SortOrder) {
        _sortOrder.value = order
    }

    fun tambahFilm(
        judul: String, 
        genre: String, 
        tahun: String, 
        sudahDitonton: Boolean,
        rating: Float = 0f,
        imageUri: String? = null,
        isFavorite: Boolean = false
    ) {
        viewModelScope.launch {
            val filmBaru = Film(
                judul = judul, 
                genre = genre, 
                tahun = tahun, 
                sudahDitonton = sudahDitonton,
                rating = rating,
                imageUri = imageUri,
                isFavorite = isFavorite
            )
            dao.insert(filmBaru)
        }
    }

    fun getFilmById(id: Int): Flow<Film?> {
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

    fun toggleFavorite(film: Film) {
        viewModelScope.launch {
            dao.update(film.copy(isFavorite = !film.isFavorite))
        }
    }

    sealed class UiEvent {
        data class ShowUndoSnackbar(val message: String) : UiEvent()
    }

    enum class SortOrder {
        ID_DESC, JUDUL_ASC, RATING_DESC, TAHUN_DESC
    }
}