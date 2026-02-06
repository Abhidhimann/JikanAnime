package com.abhishek.jikananime.data.mapper

import com.abhishek.jikananime.data.local.entity.AnimeEntity
import com.abhishek.jikananime.data.remote.response.AnimeDetailsDto
import com.abhishek.jikananime.data.remote.response.AnimeDto
import com.abhishek.jikananime.domain.model.Anime
import com.abhishek.jikananime.domain.model.AnimeDetails

fun AnimeDto.toEntity(): AnimeEntity =
    AnimeEntity(
        title = title,
        episodes = episodes ?: 0,
        rating = score ?: 0.0,
        imageUrl = images?.jpg?.imageUrl ?: "",
        animeId = animeId
    )

fun AnimeEntity.toDomain(): Anime =
    Anime(
        id = id,
        title = title,
        episodes = episodes,
        rating = rating,
        imageUrl = imageUrl,
        animeId = animeId
    )

fun AnimeDetailsDto.toDomain(): AnimeDetails =
    AnimeDetails(
        animeId = animeId,
        title = title,
        synopsis = synopsis ?: "No synopsis available",
        genres = genres.map { it.name },
        cast = cast?.map { it.name } ?: emptyList(),
        episodes = episodes ?: 0,
        rating = score ?: 0.0,
        posterUrl = images.jpg?.imageUrl ?: "",
        trailerYoutubeId = "52v_6lRR_Cw"
    )