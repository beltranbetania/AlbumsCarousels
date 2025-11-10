package com.example.albumschallenge

import android.app.Application
import com.example.albumschallenge.di.appModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}