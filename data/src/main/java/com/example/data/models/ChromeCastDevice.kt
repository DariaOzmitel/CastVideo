package com.example.data.models

import androidx.mediarouter.media.MediaRouter

data class ChromeCastDevice(val route: MediaRouter.RouteInfo) :
    Device(
        route.id,
        route.name,
        route.description,
        route.isEnabled
    )