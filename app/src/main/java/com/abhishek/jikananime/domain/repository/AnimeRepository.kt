package com.abhishek.jikananime.domain.repository

import com.abhishek.jikananime.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun observeTopAnime(query: String = ""): Flow<List<Anime>>
    suspend fun refreshTopAnime(): Result<Unit>
}