package com.abhishek.jikananime.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhishek.jikananime.data.local.entity.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    // this will help in searching also in future
    @Query("SELECT * FROM anime WHERE LOWER(title) like '%' || :query || '%' ORDER BY id DESC")
    fun observeAnime(query: String): Flow<List<AnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<AnimeEntity>)
}