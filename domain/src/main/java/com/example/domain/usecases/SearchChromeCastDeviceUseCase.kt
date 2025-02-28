package com.example.domain.usecases

import com.example.domain.repository.CastVideoRepository

class SearchChromeCastDeviceUseCase(private val repository: CastVideoRepository) {
    fun invoke() {
        return repository.searchChromeCastDevice()
    }
}