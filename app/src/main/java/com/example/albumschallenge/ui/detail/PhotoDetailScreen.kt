package com.example.albumschallenge.ui.detail

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

import com.google.gson.Gson
import androidx.navigation.NavBackStackEntry
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.albumschallenge.data.model.Photo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailScreen(
    photo: Photo,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        PhotoDetailContent(
            modifier = Modifier.padding(innerPadding),
            photo = photo
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun PhotoDetailContent(modifier: Modifier = Modifier, photo: Photo) {
    val imageUrl = remember(photo) {
        photo.url
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            shape = RoundedCornerShape(12.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                GlideImage(
                    model = imageUrl,
                    contentDescription = photo.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = photo.title ?: "",
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = photo.title ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun androidx.navigation.NavController.navigatePhoto(photo: Photo) {
    val json = Uri.encode(Gson().toJson(photo))
    this.navigate("detail?photo=$json")
}