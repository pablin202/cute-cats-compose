package com.pdm.cats

import android.app.Application
import com.pdm.cats.di.appModules
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}