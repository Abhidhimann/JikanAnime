package com.abhishek.jikananime.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.jikananime.domain.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {


    fun refreshMovies()  = viewModelScope.launch {
        animeRepository.refreshTopAnime()
    }

}