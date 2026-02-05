package com.abhishek.jikananime.data.respository

import android.util.Log
import com.abhishek.jikananime.core.tempTag
import com.abhishek.jikananime.data.remote.api.JikanApi
import com.abhishek.jikananime.di.IoDispatcher
import com.abhishek.jikananime.domain.model.Anime
import com.abhishek.jikananime.domain.repository.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val jikanApi: JikanApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AnimeRepository {
    override fun observeTopAnime(): Flow<List<Anime>> {
        return emptyFlow() // db call
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

                Result.success(Unit)

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}