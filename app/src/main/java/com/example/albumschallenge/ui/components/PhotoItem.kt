package com.example.albumschallenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.albumschallenge.data.model.Photo

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoItem(photo: Photo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        GlideImage(
            model = photo.thumbnailUrl,
            contentDescription = photo.title,
            contentScale = ContentScale.Crop
        )
    }
}