package com.adam0006.cinelist.util

const val KEY_ID_FILM = "idFilm"

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object Settings : Screen("settingsScreen")
    data object Detail : Screen("detailScreen/{$KEY_ID_FILM}") {
        fun withId(id: Int) = "detailScreen/$id"
    }
}