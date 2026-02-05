package com.abhishek.jikananime.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeScreenRoot(viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()){
    viewModel.refreshMovies()
}