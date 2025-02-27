package com.example.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.mediarouter.media.MediaControlIntent
import androidx.mediarouter.media.MediaRouteSelector
import androidx.mediarouter.media.MediaRouter
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastState
import com.google.android.gms.cast.framework.CastStateListener
import com.google.android.gms.cast.framework.media.RemoteMediaClient

enum class CastDeviceState {
    INITIAL,
    SEARCHING,
    CONNECTING,
    CONNECTED
}

open class Device(val id: String, val name: String?, val description: String?, val enable: Boolean)

data class ChromeCastDevice(val route: MediaRouter.RouteInfo) :
    Device(
        route.id,
        route.name,
        route.description,
        route.isEnabled
    )

internal class CastVideoScreenViewModel(
    private val castContext: CastContext,
    private var mediaRouter: MediaRouter
) : ViewModel() {

    private var urlToPlay by mutableStateOf("")

    var deviceState by mutableStateOf(CastDeviceState.INITIAL)
    val deviceList = mutableStateListOf<ChromeCastDevice>()
    var currentDevice by mutableStateOf<ChromeCastDevice?>(null)

    private var remoteMediaClient by mutableStateOf<RemoteMediaClient?>(null)

    fun start() {
        castContext.addCastStateListener(castStateListener())
        startSearch()
    }

    fun connect(url: String, device: ChromeCastDevice) {
        urlToPlay = url
        currentDevice = device

        deviceState = CastDeviceState.CONNECTING
        stopSearch()

        mediaRouter.selectRoute(device.route)
    }

    private fun startSearch() {
        val selector = MediaRouteSelector.Builder()
            .addControlCategory(MediaControlIntent.CATEGORY_REMOTE_PLAYBACK)
            .build()

        mediaRouter.addCallback(
            selector,
            mediaRouterCallBack(),
            MediaRouter.CALLBACK_FLAG_PERFORM_ACTIVE_SCAN
        )


        deviceState = CastDeviceState.SEARCHING
    }

    private fun stopSearch() {
        mediaRouter.removeCallback(mediaRouterCallBack())
    }

    private fun castStateListener() = CastStateListener {
        when (it) {
            CastState.CONNECTED -> {
                deviceState = CastDeviceState.CONNECTED

                val castSession = castContext.sessionManager.currentCastSession

                remoteMediaClient = castSession?.remoteMediaClient
                remoteMediaClient?.stop()

                val videoMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE).apply {
                    putString(MediaMetadata.KEY_TITLE, urlToPlay)
                }

                val mediaInfo = MediaInfo.Builder(urlToPlay)
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setMetadata(videoMetadata)

                val mediaLoadRequestData = MediaLoadRequestData.Builder()
                    .setMediaInfo(mediaInfo.build())
                    .setAutoplay(true)

                remoteMediaClient?.load(mediaLoadRequestData.build())

                stopSearch()
            }
        }
    }

    private fun mediaRouterCallBack() = object : MediaRouter.Callback() {
        override fun onRouteAdded(router: MediaRouter, route: MediaRouter.RouteInfo) {
            if (!deviceList.any { it.id == route.id } && route.isEnabled)
                deviceList.add(ChromeCastDevice(route = route))
        }

        override fun onRouteRemoved(router: MediaRouter, route: MediaRouter.RouteInfo) {
            val deviceById = deviceList.find { it.id == route.id }
            deviceList.remove(deviceById)
        }
    }
}