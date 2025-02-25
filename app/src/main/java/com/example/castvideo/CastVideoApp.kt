package com.example.castvideo

import android.app.Application
import com.example.data.di.dataModule
import com.example.ui.di.uiModule
import com.example.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CastVideoApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CastVideoApp)
            modules(listOf(dataModule, domainModule, uiModule))
        }
    }
}