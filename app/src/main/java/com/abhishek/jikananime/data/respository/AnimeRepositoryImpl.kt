package com.abhishek.jikananime.data.respository

import android.util.Log
import com.abhishek.jikananime.core.DataError
import com.abhishek.jikananime.core.classTag
import com.abhishek.jikananime.data.local.dao.AnimeDao
import com.abhishek.jikananime.data.local.pref.AnimePrefs
import com.abhishek.jikananime.data.mapper.toDomain
import com.abhishek.jikananime.data.mapper.toEntity
import com.abhishek.jikananime.data.remote.api.JikanApi
import com.abhishek.jikananime.di.IoDispatcher
import com.abhishek.jikananime.domain.model.Anime
import com.abhishek.jikananime.domain.model.AnimeDetails
import com.abhishek.jikananime.domain.repository.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeDao: AnimeDao,
    private val jikanApi: JikanApi,
    private val animePrefs: AnimePrefs,
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
                val nextPage = animePrefs.currentPage + 1
                if (nextPage > animePrefs.totalPages) {
                    return@withContext Result.failure(DataError.LimitReached)
                }

                Log.i(this@AnimeRepositoryImpl.classTag(), "fetching anime with page $nextPage")

                val response = jikanApi.getTopAnime(nextPage)

                if (!response.isSuccessful) {
                    return@withContext Result.failure(
                        DataError.NetworkError(
                            response.code(),
                            response.message()
                        )
                    )
                }

                val body = response.body()
                    ?: return@withContext Result.failure(
                        DataError.EmptyBody
                    )

                Log.i(this@AnimeRepositoryImpl.classTag(), "response is $body")

                val entities = body.data.map { it.toEntity() }
                animeDao.insertAll(entities)
                animePrefs.currentPage = response.body()?.pagination?.currentPage ?: 0
                animePrefs.totalPages = response.body()?.pagination?.totalPages ?: animePrefs.totalPages

                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(DataError.UnknownError(e))
            }
        }
    }

    override suspend fun getAnimeDetails(animeId: Int): Result<AnimeDetails> =
        withContext(dispatcher) {
            try {
                val response = jikanApi.getAnimeDetails(animeId)

                if (!response.isSuccessful) {
                    return@withContext Result.failure(
                        DataError.NetworkError(
                            response.code(),
                            response.message()
                        )
                    )
                }

                val body = response.body()
                    ?: return@withContext Result.failure(
                        DataError.EmptyBody
                    )

                Log.i(this@AnimeRepositoryImpl.classTag(), "response is $body")
                Result.success(body.data.toDomain())
            } catch (e: Exception) {
                Result.failure(DataError.UnknownError(e))
            }
        }
}