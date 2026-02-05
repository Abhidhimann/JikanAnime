package com.abhishek.jikananime.data.remote.response

import com.google.gson.annotations.SerializedName

data class AnimeDto(
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val images: ImagesDto?
)

data class ImagesDto(
    val jpg: JpgDto?
)

data class JpgDto(
    @SerializedName("image_url")
    val imageUrl: String?
)