package com.example.data.di

import com.example.data.repository.CastVideoRepositoryImpl
import com.example.domain.repository.CastVideoRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::CastVideoRepositoryImpl) bind CastVideoRepository::class
}