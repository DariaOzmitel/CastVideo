package com.example.domain.repository

import com.example.domain.models.CastDeviceState
import com.example.domain.models.ChromeCastDeviceDomain
import kotlinx.coroutines.flow.Flow

interface CastVideoRepository {
    fun castVideo(id: String)
    fun searchChromeCastDevice()
    fun getDeviceList(): Flow<List<ChromeCastDeviceDomain>>
    fun getCastDeviceState(): Flow<CastDeviceState>
}