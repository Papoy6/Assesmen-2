package com.adam0006.cinelist.model

// Model untuk data film dari API
// Field harus sama persis dengan nama key di JSON response API
data class Film(
    val id: String = "",
    val judul: String = "",
    val genre: String = "",
    val imageId: String = "",
    val userId: String = ""      // untuk tahu apakah film ini milik user yang login
)