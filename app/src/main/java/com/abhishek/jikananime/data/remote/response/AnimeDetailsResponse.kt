package com.abhishek.jikananime.data.remote.response

import com.google.gson.annotations.SerializedName

data class AnimeDetailsResponse(
    val data: AnimeDetailsDto
)

data class AnimeDetailsDto(
    @SerializedName("mal_id")
    val animeId: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val genres: List<GenreDto>,
    val images: ImagesDto,
    val trailer: TrailerDto?,
    val cast: List<CastDto>?
)

data class GenreDto(val name: String)

data class TrailerDto(
    @SerializedName("youtube_id")
    val youtubeId: String?
)

data class CastDto(
    val name: String
)