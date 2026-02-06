package com.abhishek.jikananime.presentation.screens.animedetails

import com.abhishek.jikananime.domain.model.AnimeDetails

data class AnimeDetailUiState(
    val anime: AnimeDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)