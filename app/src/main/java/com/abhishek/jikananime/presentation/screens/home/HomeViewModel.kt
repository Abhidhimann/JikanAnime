package com.abhishek.jikananime.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.jikananime.core.DataError
import com.abhishek.jikananime.core.NetworkMonitor
import com.abhishek.jikananime.core.classTag
import com.abhishek.jikananime.domain.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<HomeEvent>()
    val events = _events.asSharedFlow()

    val isConnected = networkMonitor.isConnectedFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            animeRepository.observeTopAnime().collect { list ->
                if (list.isEmpty()) {
                    // app first launch
                    refreshAnime()
                } else
                    _uiState.update { it.copy(animeList = list) }
            }
        }
    }

    fun refreshAnime() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        if (!isConnected.value) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                )
            }
            _events.emit(HomeEvent.ShowToast("No Internet Connection!"))
            return@launch
        }

        animeRepository.refreshTopAnime().onSuccess {
            _uiState.update { it.copy(isLoading = false) }
        }.onFailure { exception ->
            Log.e(classTag(), "Fetch animes from network $exception")
            // later can make error handler, later can segregate error code to different message
            val message = when (exception) {
                is DataError.LimitReached -> "No more anime found"
                is DataError.NetworkError -> "Network error occurred!"
                is DataError.EmptyBody -> "Some error occurred!"
                else -> "Some error occurred!"
            }
            _uiState.update {
                it.copy(
                    isLoading = false,
                )
            }
            _events.emit(HomeEvent.ShowToast(message))
        }
    }
}