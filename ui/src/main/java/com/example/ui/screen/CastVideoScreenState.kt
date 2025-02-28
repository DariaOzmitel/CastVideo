package com.example.ui.screen

import com.example.domain.models.CastDeviceState
import com.example.domain.models.ChromeCastDeviceDomain

internal data class CastVideoScreenState(
    val deviceState: CastDeviceState,
    val deviceList: List<ChromeCastDeviceDomain>? = null
)