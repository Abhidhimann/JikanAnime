package com.abhishek.jikananime.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AnimeDetails : Screen("anime_details/{animeId}") {
        const val ARG_ANIME_ID = "animeId"
        fun createRoute(animeId: Int) = "anime_details/$animeId"
    }
}