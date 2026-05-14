package com.adam0006.cinelist.database

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val db = FilmDb.getInstance(context)
            return MainViewModel(db.dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}