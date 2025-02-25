package com.example.ui.di

import com.example.ui.screen.CastVideoScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::CastVideoScreenViewModel)
}