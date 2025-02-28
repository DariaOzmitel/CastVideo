package com.example.domain.usecases

import com.example.domain.repository.CastVideoRepository

class CastVideoUseCase(
    private val repository: CastVideoRepository
) {
    fun invoke(id: String) {
        return repository.castVideo(id)
    }
}