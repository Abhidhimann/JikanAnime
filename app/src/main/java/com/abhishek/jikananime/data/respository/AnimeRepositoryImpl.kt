package com.abhishek.jikananime.data.respository

import android.util.Log
import com.abhishek.jikananime.core.tempTag
import com.abhishek.jikananime.data.local.dao.AnimeDao
import com.abhishek.jikananime.data.mapper.toDomain
import com.abhishek.jikananime.data.mapper.toEntity
import com.abhishek.jikananime.data.remote.api.JikanApi
import com.abhishek.jikananime.di.IoDispatcher
import com.abhishek.jikananime.domain.model.Anime
import com.abhishek.jikananime.domain.repository.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeDao: AnimeDao,
    private val jikanApi: JikanApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AnimeRepository {
    override fun observeTopAnime(query: String): Flow<List<Anime>> {
        return animeDao.observeAnime(query)
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun refreshTopAnime(): Result<Unit> {
        return withContext(dispatcher) {
            try {
                val response = jikanApi.getTopAnime()

                if (!response.isSuccessful) {
                    return@withContext Result.failure(
                        Exception("API error: ${response.code()}")
                    )
                }

                val body = response.body()
                    ?: return@withContext Result.failure(
                        Exception("Empty response body")
                    )

                Log.d(tempTag(), "response is $body")

                val entities = body.data.map { it.toEntity() }
                animeDao.insertAll(entities)

                Result.success(Unit)

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}