package com.example.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.elements.CastVideoButton
import com.example.ui.elements.DeviceItem
import org.koin.androidx.compose.koinViewModel

const val VIDEO_URL =
    "https://videolink-test.mycdn.me/?pct=1&sig=6QNOvp0y3BE&ct=0&clientType=45&mid=193241622673&type=5"

@Composable
fun CastVideoScreen(
    innerPadding: PaddingValues,
) {
    val castViewModel: CastVideoScreenViewModel = koinViewModel()

    val deviceList = castViewModel.deviceList
    val deviceState = castViewModel.deviceState
    val currentDevice = castViewModel.currentDevice

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(
                top = innerPadding.calculateTopPadding() + 32.dp,
                bottom = innerPadding.calculateBottomPadding() + 32.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        DeviceContent(
            modifier = Modifier.weight(1f),
            deviceList = deviceList,
            deviceState = deviceState,
            currentDevice = currentDevice
        )
        {
            castViewModel.connect(VIDEO_URL, it)
        }
        CastVideoButton(
            text = stringResource(R.string.cast_video)
        ) {
            castViewModel.start()
        }
    }
}

@Composable
private fun DeviceContent(
    modifier: Modifier = Modifier,
    currentDevice: ChromeCastDevice?,
    deviceState: CastDeviceState,
    deviceList: List<ChromeCastDevice>,
    onDeviceItemClickListener: (ChromeCastDevice) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        when (deviceState) {
            CastDeviceState.INITIAL -> {}
            CastDeviceState.CONNECTED -> {
                if (currentDevice != null) {
                    DeviceItem(device = currentDevice, icon = {
                        Icon(painter = painterResource(R.drawable.cast), contentDescription = null)
                    })
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                    }
                }
            }

            CastDeviceState.CONNECTING -> {

                if (currentDevice != null) {
                    DeviceItem(device = currentDevice, icon = {
                        Icon(
                            painter = painterResource(R.drawable.cast),
                            contentDescription = null
                        )
                    }, loading = true)
                }
            }

            CastDeviceState.SEARCHING -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }
                deviceList.forEach {

                    DeviceItem(
                        onClick = { onDeviceItemClickListener(it) },
                        device = it,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.cast),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}