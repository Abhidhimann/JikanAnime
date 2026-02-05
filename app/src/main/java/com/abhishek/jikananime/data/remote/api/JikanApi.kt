package com.abhishek.jikananime.data.remote.api

import com.abhishek.jikananime.data.remote.response.AnimeDetailsResponse
import com.abhishek.jikananime.data.remote.response.TopAnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApi {

    @GET("v4/top/anime")
    suspend fun getTopAnime(): Response<TopAnimeResponse>

    @GET("v4/anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") animeId: Int
    ): Response<AnimeDetailsResponse>
}