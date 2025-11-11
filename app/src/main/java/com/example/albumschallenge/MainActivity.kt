package com.example.albumschallenge

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.albumschallenge.data.model.Photo
import com.example.albumschallenge.ui.detail.PhotoDetailScreen
import com.example.albumschallenge.ui.detail.navigatePhoto
import com.example.albumschallenge.ui.home.HomeScreen
import com.example.albumschallenge.ui.theme.AlbumsChallengeTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlbumsChallengeTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable("home") {
                HomeScreen(
                    onPhotoClick = { photo ->
                        navController.navigatePhoto(photo)
                    },
                    onSeeMore = { albumId ->
                        Toast.makeText(context, context.getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
                    }
                )
            }

            composable("detail?photo={photo}") {
                val json = it.arguments?.getString("photo") ?: ""
                val photo = Gson().fromJson(Uri.decode(json), Photo::class.java)

                PhotoDetailScreen(
                    photo = photo,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
