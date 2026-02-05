package com.abhishek.jikananime.domain.model

data class Anime(
    val id: Int,
    val title: String,
    val episodes: Int,
    val rating: Double,
    val imageUrl: String
)

val dummyAnimeList = listOf(
    Anime(
        id = 1,
        title = "Fullmetal Alchemist: Brotherhood",
        episodes = 64,
        rating = 9.1,
        imageUrl = "https://cdn.myanimelist.net/images/anime/1223/96541.jpg"
    ),
    Anime(
        id = 2,
        title = "Attack on Titan",
        episodes = 75,
        rating = 8.9,
        imageUrl = "https://cdn.myanimelist.net/images/anime/10/47347.jpg"
    ),
    Anime(
        id = 3,
        title = "Death Note",
        episodes = 37,
        rating = 8.6,
        imageUrl = "https://cdn.myanimelist.net/images/anime/9/9453.jpg"
    ),
    Anime(
        id = 4,
        title = "Naruto: Shippuden",
        episodes = 500,
        rating = 8.2,
        imageUrl = "https://cdn.myanimelist.net/images/anime/5/17407.jpg"
    ),
)
