package com.example.data.mapper

import com.example.data.models.ChromeCastDevice
import com.example.domain.models.ChromeCastDeviceDomain


internal class CastVideoMapper {
    fun chromeCastDeviceListToChromeCastDeviceDomain(deviceList: List<ChromeCastDevice>) =
        deviceList.map { chromeCastDeviceToChromeCastDeviceDomain(it) }

    private fun chromeCastDeviceToChromeCastDeviceDomain(device: ChromeCastDevice) =
        device.run {
            ChromeCastDeviceDomain(
                id = id,
                name = name,
                description = description,
                enable = enable
            )
        }
}