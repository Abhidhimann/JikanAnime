package com.abhishek.jikananime.presentation.screens.animedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.jikananime.domain.repository.AnimeRepository
import com.abhishek.jikananime.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val animeId: Int =
        checkNotNull(savedStateHandle[Screen.AnimeDetails.ARG_ANIME_ID]) // handled in navigation component so safe

    private val _uiState = MutableStateFlow(AnimeDetailUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadAnimeDetails()
    }

    fun loadAnimeDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            animeRepository.getAnimeDetails(animeId)
                .onSuccess { animeDetails ->
                    _uiState.update {
                        it.copy(
                            anime = animeDetails,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            // temporary, user custom defined error message
                            error = e.message ?: "Unknown error"
                        )
                    }
                }
        }
    }
}