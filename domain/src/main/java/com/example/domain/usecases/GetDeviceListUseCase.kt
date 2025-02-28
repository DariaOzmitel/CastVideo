package com.example.domain.usecases

import com.example.domain.models.ChromeCastDeviceDomain
import com.example.domain.repository.CastVideoRepository
import kotlinx.coroutines.flow.Flow

class GetDeviceListUseCase(
    private val repository: CastVideoRepository
) {
    fun invoke(): Flow<List<ChromeCastDeviceDomain>> {
        return repository.getDeviceList()
    }
}