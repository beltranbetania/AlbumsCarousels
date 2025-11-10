package com.example.albumschallenge.data.network

import com.example.albumschallenge.data.model.Album
import com.example.albumschallenge.data.model.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("photos")
    suspend fun getPhotosByAlbum(@Query("albumId") albumId: Int): List<Photo>
}