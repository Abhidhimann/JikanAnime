package com.abhishek.jikananime.presentation.screens.home

// for 1 time event only
sealed interface HomeEvent {
    data class ShowToast(val message: String): HomeEvent
}