package com.abhishek.jikananime.domain.model

data class Anime(
    val id: Int,
    val title: String,
    val episodes: Int,
    val rating: Double,
    val imageUrl: String,
    val animeId: Int,
)

val dummyAnimeList = listOf(
    Anime(
        id = 1,
        title = "Dhiman",
        episodes = 1,
        rating = 2.0,
        imageUrl = "",
        animeId = 1
    ),
    Anime(
        id = 2,
        title = "Abhishek",
        episodes = 1,
        rating = 3.4,
        imageUrl = "",
        animeId = 2
    )
)
