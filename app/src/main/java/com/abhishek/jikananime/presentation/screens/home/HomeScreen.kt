package com.abhishek.jikananime.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.abhishek.jikananime.domain.model.Anime
import com.abhishek.jikananime.domain.model.dummyAnimeList
import com.abhishek.jikananime.presentation.utils.AnimePoster

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    onAnimeClick: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        viewModel.events
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { event ->
                when (event) {
                    is HomeEvent.ShowToast -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    HomeScreen(
        state = state,
        onRefresh = { viewModel.refreshAnime() },
        onAnimeClick = { onAnimeClick(it) }
    )
}

@Composable
fun HomeScreen(state: HomeUiState, onRefresh: () -> Unit, onAnimeClick: (Int) -> Unit) {
    val pullRefreshState = rememberPullToRefreshState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding(),
            ),
        color = MaterialTheme.colorScheme.background
    ) {
        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = onRefresh,
            state = pullRefreshState,
        ) {
            when {
                state.isLoading && state.animeList.isEmpty() -> {
                    HomeShimmerList()
                }

                else -> {
                    AnimeGridLayout(state, onAnimeClick)
                }
            }
        }
    }
}

@Composable
fun HomeShimmerList() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(10) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Box(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun AnimeGridLayout(state: HomeUiState, onAnimeClick: (Int) -> Unit) {
    val listState = rememberLazyGridState()
    val animeList = state.animeList

    // after refresh scrolling to 0th pos
    LaunchedEffect(state) {
        if (!state.isLoading && state.animeList.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }

    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = animeList,
            key = { anime -> anime.id }
        ) { anime ->
            AnimeGridItem(anime) {
                onAnimeClick(anime.animeId)
            }
        }
    }
}


@Composable
fun AnimeGridItem(anime: Anime, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(anime.id) },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column {
            AnimePoster(
                imageUrl = anime.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f),
                shape = RectangleShape
            )

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = anime.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${anime.rating}",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Text(
                        text = "Ep: ${anime.episodes}",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(HomeUiState(dummyAnimeList), {}, {})
}
