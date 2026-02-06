package com.abhishek.jikananime.data.remote.response

import com.google.gson.annotations.SerializedName

data class TopAnimeResponse(
    val data: List<AnimeDto>,
    val pagination: Pagination,
)

data class Pagination(
    @SerializedName("last_visible_page")
    val totalPages: Int,
    @SerializedName("current_page")
    val currentPage: Int,
)