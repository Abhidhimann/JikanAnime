package com.abhishek.jikananime.presentation.screens.animedetails

import com.abhishek.jikananime.domain.model.AnimeDetails

sealed interface AnimeDetailUiState {
    data object Loading : AnimeDetailUiState
    data class Success(val anime: AnimeDetails) : AnimeDetailUiState
    data class Error(val message: String) : AnimeDetailUiState
}