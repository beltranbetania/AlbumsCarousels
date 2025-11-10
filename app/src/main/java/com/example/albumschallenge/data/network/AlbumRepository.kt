package com.example.albumschallenge.data.network

import com.example.albumschallenge.data.model.Album
import com.example.albumschallenge.data.model.Photo

class AlbumRepository(private val api: ApiService) {
    suspend fun getAlbums(): List<Album> = api.getAlbums()
    suspend fun getPhotosByAlbum(albumId: Int): List<Photo> = api.getPhotosByAlbum(albumId)
}