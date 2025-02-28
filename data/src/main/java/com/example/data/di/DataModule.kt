package com.example.data.di

import android.app.Application
import androidx.mediarouter.media.MediaRouter
import com.example.data.mapper.CastVideoMapper
import com.example.data.repository.CastVideoRepositoryImpl
import com.example.domain.repository.CastVideoRepository
import com.google.android.gms.cast.framework.CastContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::CastVideoRepositoryImpl) bind CastVideoRepository::class
    singleOf(::CastVideoMapper)
    singleOf(::provideCastContext)
    singleOf(::provideMediaRouter)
}


private fun provideMediaRouter(application: Application): MediaRouter {
    return MediaRouter.getInstance(application)
}

private fun provideCastContext(application: Application): CastContext {
    return CastContext.getSharedInstance(application)
}