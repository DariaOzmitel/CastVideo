package com.example.domain.usecases

import com.example.domain.models.CastDeviceState
import com.example.domain.repository.CastVideoRepository
import kotlinx.coroutines.flow.Flow

class GetDeviceStateUseCase(
    private val repository: CastVideoRepository
) {
    fun invoke(): Flow<CastDeviceState> {
        return repository.getCastDeviceState()
    }
}