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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.CastDeviceState
import com.example.domain.models.ChromeCastDeviceDomain
import com.example.ui.R
import com.example.ui.elements.CastVideoButton
import com.example.ui.elements.DeviceItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CastVideoScreen(
    innerPadding: PaddingValues,
) {
    val castViewModel: CastVideoScreenViewModel = koinViewModel()
    val state by castViewModel.getCastVideoScreenState().collectAsStateWithLifecycle()


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
            deviceList = state.deviceList,
            deviceState = state.deviceState,
        )
        {
            castViewModel.connect(it)
        }
        CastVideoButton(
            text = stringResource(R.string.cast_video)
        ) {
            castViewModel.searchChromeCastDevice()
        }
    }
}

@Composable
private fun DeviceContent(
    modifier: Modifier = Modifier,
    deviceState: CastDeviceState,
    deviceList: List<ChromeCastDeviceDomain>?,
    onDeviceItemClickListener: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        when (deviceState) {
            CastDeviceState.INITIAL -> {}
            CastDeviceState.CONNECTED -> {
                deviceList?.forEach {

                    DeviceItem(
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

            CastDeviceState.CONNECTING -> {

                deviceList?.forEach {

                    DeviceItem(
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

            CastDeviceState.SEARCHING -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }
                deviceList?.forEach {

                    DeviceItem(
                        onClick = { onDeviceItemClickListener(it.id) },
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