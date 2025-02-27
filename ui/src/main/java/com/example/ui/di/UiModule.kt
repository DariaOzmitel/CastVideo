package com.example.ui.di

import android.app.Application
import androidx.mediarouter.media.MediaRouter
import com.example.ui.screen.CastVideoScreenViewModel
import com.google.android.gms.cast.framework.CastContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::CastVideoScreenViewModel)
    singleOf(::provideCastContext)
    singleOf(::provideMediaRouter)
}

private fun provideMediaRouter(application: Application): MediaRouter {
    return MediaRouter.getInstance(application)
}

private fun provideCastContext(application: Application): CastContext {
    return CastContext.getSharedInstance(application)
}