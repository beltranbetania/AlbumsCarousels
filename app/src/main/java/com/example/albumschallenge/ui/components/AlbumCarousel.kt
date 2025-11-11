package com.example.albumschallenge.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.albumschallenge.data.model.Album
import com.example.albumschallenge.data.model.Photo


@Composable
fun AlbumCarousel(
    album: Album,
    photos: List<Photo>,
    onPhotoClick: (Photo) -> Unit,
    onSeeMoreClick: () -> Unit
) {
    Column(Modifier.padding(8.dp)) {
        Text(album.title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        LazyRow {
            items(photos) { photo ->
                PhotoItem(photo, onClick = { onPhotoClick(photo) })
            }
        }
    }
}