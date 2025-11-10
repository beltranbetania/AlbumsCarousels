package com.example.albumschallenge.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albumschallenge.data.model.Album
import com.example.albumschallenge.data.model.Photo
import com.example.albumschallenge.data.network.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AlbumWithPhotos(val album: Album, val photos: List<Photo>)
data class HomeUiState(
    val isLoading: Boolean = false,
    val data: List<AlbumWithPhotos>? = null,
    val error: String? = null
)

class HomeViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState(isLoading = true))
    val state: StateFlow<HomeUiState> = _state

    init { loadData() }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val albums = repository.getAlbums()
                val albumsWithPhotos = albums.map { album ->
                    val photos = repository.getPhotosByAlbum(album.id).take(10)
                    AlbumWithPhotos(album, photos)
                }
                _state.value = HomeUiState(data = albumsWithPhotos)
            } catch (e: Exception) {
                _state.value = HomeUiState(error = e.localizedMessage)
            }
        }
    }
}
