package com.example.albumschallenge.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.albumschallenge.data.model.Photo
import com.example.albumschallenge.ui.components.AlbumCarousel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onPhotoClick: (Photo) -> Unit,
    onSeeMore: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Albums") }
            )
        }
    ) { innerPadding ->
        when {
            state.isLoading -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            state.error != null -> Text(
                "Error: ${state.error}",
                modifier = Modifier.padding(innerPadding)
            )
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
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
                }
            }
        }
    }
}
