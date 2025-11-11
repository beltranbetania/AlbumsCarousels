package com.example.albumschallenge.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albumschallenge.data.model.Album
import com.example.albumschallenge.data.model.Photo
import com.example.albumschallenge.data.network.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AlbumWithPhotos(val album: Album, val photos: List<Photo>)

data class HomeUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val data: List<AlbumWithPhotos>? = null,
    val error: String? = null
)

class HomeViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState(isLoading = true))
    val state: StateFlow<HomeUiState> = _state

    private var currentStart = 0
    private val limit = 10
    private var isLastPage = false

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLastPage || _state.value.isLoadingMore) return

        viewModelScope.launch {
            val isFirstPage = currentStart == 0
            _state.update {
                it.copy(
                    isLoading = isFirstPage,
                    isLoadingMore = !isFirstPage
                )
            }

            try {
                val albums = repository.getAlbums(currentStart, limit)
                val albumsWithPhotos = albums.map { album ->
                    val photos = repository.getPhotosByAlbum(album.id).take(10)
                    AlbumWithPhotos(album, photos)
                }

                _state.update {
                    val currentData = it.data.orEmpty()
                    it.copy(
                        data = currentData + albumsWithPhotos,
                        isLoading = false,
                        isLoadingMore = false
                    )
                }

                if (albums.isEmpty() || albums.size < limit) {
                    isLastPage = true
                } else {
                    currentStart += limit
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.localizedMessage,
                        isLoading = false,
                        isLoadingMore = false
                    )
                }
            }
        }
    }
}
