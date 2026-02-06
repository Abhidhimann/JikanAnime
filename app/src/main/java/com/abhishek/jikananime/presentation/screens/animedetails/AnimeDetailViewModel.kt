package com.abhishek.jikananime.presentation.screens.animedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.jikananime.domain.repository.AnimeRepository
import com.abhishek.jikananime.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val animeId: Int =
        checkNotNull(savedStateHandle[Screen.AnimeDetails.ARG_ANIME_ID]) // handled in navigation component so safe

    private val _state =
        MutableStateFlow<AnimeDetailUiState>(AnimeDetailUiState.Loading)
    val state: StateFlow<AnimeDetailUiState> = _state

    init {
        loadAnimeDetails()
    }

    fun loadAnimeDetails() {
        viewModelScope.launch {
            _state.value = AnimeDetailUiState.Loading

            animeRepository.getAnimeDetails(animeId)
                .onSuccess {
                    _state.value = AnimeDetailUiState.Success(it)
                }
                .onFailure {
                    _state.value = AnimeDetailUiState.Error(
                        it.message ?: "Something went wrong"
                    )
                }
        }
    }
}