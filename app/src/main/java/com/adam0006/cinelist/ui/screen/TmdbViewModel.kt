package com.adam0006.cinelist.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adam0006.cinelist.model.TmdbMovie
import com.adam0006.cinelist.network.ApiStatus
import com.adam0006.cinelist.network.TmdbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TmdbViewModel : ViewModel() {

    var movies = mutableStateOf(emptyList<TmdbMovie>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                movies.value = TmdbApi.service.getPopularMovies().results
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("TmdbViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
}