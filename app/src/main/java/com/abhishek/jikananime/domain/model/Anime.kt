package com.abhishek.jikananime.domain.model

data class Anime(
    val id: Int,
    val title: String,
    val episodes: Int,
    val rating: Double,
    val imageUrl: String
)
