package com.example.albumschallenge.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.albumschallenge.data.model.Photo
import com.example.albumschallenge.ui.components.AlbumCarousel
import org.koin.androidx.compose.koinViewModel

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onPhotoClick: (Photo) -> Unit,
    onSeeMore: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItem ->
                val totalItems = listState.layoutInfo.totalItemsCount
                if (lastVisibleItem == totalItems - 1 && !state.isLoadingMore) {
                    viewModel.loadNextPage()
                }
            }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Albums") }) }
    ) { innerPadding ->
        when {
            state.isLoading -> Box(
                Modifier.fillMaxSize().padding(innerPadding),
                Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            state.error != null -> Box(
                Modifier.fillMaxSize().padding(innerPadding),
                Alignment.Center
            ) {
                Text("Error: ${state.error}")
            }

            else -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                ) {
                    state.data?.forEach { albumWithPhotos ->
                        item {
                            AlbumCarousel(
                                album = albumWithPhotos.album,
                                photos = albumWithPhotos.photos,
                                onPhotoClick = onPhotoClick,
                                onSeeMoreClick = { onSeeMore(albumWithPhotos.album.id) }
                            )
                        }
                    }

                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}
