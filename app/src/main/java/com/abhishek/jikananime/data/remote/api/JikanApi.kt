package com.abhishek.jikananime.data.remote.api

import com.abhishek.jikananime.data.remote.response.TopAnimeResponse
import retrofit2.Response
import retrofit2.http.GET

interface JikanApi {

    @GET("v4/top/anime")
    suspend fun getTopAnime(): Response<TopAnimeResponse>
}