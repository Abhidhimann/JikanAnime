package com.abhishek.jikananime.data.mapper

import com.abhishek.jikananime.data.local.entity.AnimeEntity
import com.abhishek.jikananime.data.remote.response.AnimeDto
import com.abhishek.jikananime.domain.model.Anime

fun AnimeDto.toEntity(): AnimeEntity =
    AnimeEntity(
        title = title,
        episodes = episodes ?: 0,
        rating = score ?: 0.0,
        imageUrl = images?.jpg?.imageUrl ?: ""
    )

fun AnimeEntity.toDomain(): Anime =
    Anime(
        id = id,
        title = title,
        episodes = episodes,
        rating = rating,
        imageUrl = imageUrl
    )