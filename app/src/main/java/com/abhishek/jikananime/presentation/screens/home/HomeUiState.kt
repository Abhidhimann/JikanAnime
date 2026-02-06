package com.abhishek.jikananime.presentation.screens.home

import com.abhishek.jikananime.domain.model.Anime

data class HomeUiState(
    val animeList: List<Anime> = emptyList(),
    val isLoading: Boolean = false,
)