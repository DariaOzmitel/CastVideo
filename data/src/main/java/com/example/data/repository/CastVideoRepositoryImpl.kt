package com.example.data.repository

import androidx.mediarouter.media.MediaControlIntent
import androidx.mediarouter.media.MediaRouteSelector
import androidx.mediarouter.media.MediaRouter
import com.example.data.mapper.CastVideoMapper
import com.example.data.models.ChromeCastDevice
import com.example.domain.models.CastDeviceState
import com.example.domain.models.ChromeCastDeviceDomain
import com.example.domain.repository.CastVideoRepository
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastState
import com.google.android.gms.cast.framework.CastStateListener
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

internal class CastVideoRepositoryImpl(
    private val castContext: CastContext,
    private var mediaRouter: MediaRouter,
    private val mapper: CastVideoMapper
) : CastVideoRepository {

    override fun castVideo(id: String) {
        deviceState.tryEmit(CastDeviceState.CONNECTING)
        stopSearch()

        val selectedDevice = deviceList.replayCache.firstOrNull()?.find { it.id == id }

        if (selectedDevice != null) {
            mediaRouter.selectRoute(selectedDevice.route)
        }
    }

    override fun searchChromeCastDevice() {
        castContext.addCastStateListener(castStateListener())
        startSearch()
    }

    override fun getDeviceList(): Flow<List<ChromeCastDeviceDomain>> {
        return deviceList.map { mapper.chromeCastDeviceListToChromeCastDeviceDomain(it) }
    }

    override fun getCastDeviceState(): Flow<CastDeviceState> {
        return deviceState
    }

    private val deviceList = MutableSharedFlow<List<ChromeCastDevice>>(replay = 1)
    private val deviceState = MutableStateFlow(CastDeviceState.INITIAL)
    private var remoteMediaClient : RemoteMediaClient? = null

    private fun startSearch(){
        val selector = MediaRouteSelector.Builder()
            .addControlCategory(MediaControlIntent.CATEGORY_REMOTE_PLAYBACK)
            .build()

        mediaRouter.addCallback(
            selector,
            mediaRouterCallback(),
            MediaRouter.CALLBACK_FLAG_PERFORM_ACTIVE_SCAN
        )
        deviceState.tryEmit(CastDeviceState.SEARCHING)
    }

    private fun stopSearch() {
        mediaRouter.removeCallback(mediaRouterCallback())
    }

    private fun mediaRouterCallback() = object : MediaRouter.Callback() {
        override fun onRouteAdded(router: MediaRouter, route: MediaRouter.RouteInfo) {
            if (route.isEnabled) {
                val currentList = deviceList.replayCache.firstOrNull().orEmpty()
                if (currentList.none { it.id == route.id }) {
                    val updatedList = currentList + ChromeCastDevice(route = route)
                    deviceList.tryEmit(updatedList)
                }
            }
        }

        override fun onRouteRemoved(router: MediaRouter, route: MediaRouter.RouteInfo) {
            val currentList = deviceList.replayCache.firstOrNull().orEmpty()
            val updatedList = currentList.filterNot { it.id == route.id }
            deviceList.tryEmit(updatedList)
        }
    }

    private fun castStateListener() = CastStateListener {
        if (it == CastState.CONNECTED) {
            deviceState.tryEmit(CastDeviceState.CONNECTED)

            val castSession = castContext.sessionManager.currentCastSession
            remoteMediaClient = castSession?.remoteMediaClient

            val mediaInfo = MediaInfo.Builder(VIDEO_URL)
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setMetadata(MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE).apply {
                    putString(MediaMetadata.KEY_TITLE, VIDEO_URL)
                })
                .build()

            remoteMediaClient?.load(
                MediaLoadRequestData.Builder()
                    .setMediaInfo(mediaInfo)
                    .setAutoplay(true)
                    .build()
            )

            stopSearch()
        }
    }
}

const val VIDEO_URL =
    "https://videolink-test.mycdn.me/?pct=1&sig=6QNOvp0y3BE&ct=0&clientType=45&mid=193241622673&type=5"