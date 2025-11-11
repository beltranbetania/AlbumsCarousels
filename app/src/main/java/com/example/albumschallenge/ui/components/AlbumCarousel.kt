package com.example.albumschallenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.albumschallenge.R
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = album.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = stringResource(R.string.see_more),
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .clickable { onSeeMoreClick() }
                    .padding(start = 8.dp)
            )
        }

        Spacer(Modifier.height(8.dp))

        LazyRow {
            items(photos) { photo ->
                PhotoItem(photo, onClick = { onPhotoClick(photo) })
            }
        }
    }
}
