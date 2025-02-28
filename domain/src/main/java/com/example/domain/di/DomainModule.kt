package com.example.domain.di

import com.example.domain.usecases.CastVideoUseCase
import com.example.domain.usecases.GetDeviceListUseCase
import com.example.domain.usecases.GetDeviceStateUseCase
import com.example.domain.usecases.SearchChromeCastDeviceUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::CastVideoUseCase)
    factoryOf(::SearchChromeCastDeviceUseCase)
    factoryOf(::GetDeviceListUseCase)
    factoryOf(::GetDeviceStateUseCase)
}