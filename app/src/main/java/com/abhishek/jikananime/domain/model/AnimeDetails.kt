package com.abhishek.jikananime.domain.model

data class AnimeDetails(
    val animeId: Int,
    val title: String,
    val synopsis: String,
    val genres: List<String>,
    val cast: List<String>,
    val episodes: Int,
    val rating: Double,
    val posterUrl: String,
    val trailerYoutubeId: String
)

val dummyAnimeDetails = AnimeDetails(
    animeId = 1,
    title = "Abhishek",
    synopsis = "This is ssfklsjfslkjfdlskjflksdjflskj",
    genres = emptyList(),
    cast = emptyList(),
    episodes = 10,
    rating = 9.0,
    posterUrl = "",
    trailerYoutubeId = ""
)