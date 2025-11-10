package com.example.albumschallenge.di

import com.example.albumschallenge.data.network.AlbumRepository
import com.example.albumschallenge.data.network.ApiService
import com.example.albumschallenge.data.network.RetrofitInstance
import com.example.albumschallenge.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ApiService> { RetrofitInstance.api }
    single { AlbumRepository(get()) }
    viewModel { HomeViewModel(get()) }
}