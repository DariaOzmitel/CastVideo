package com.example.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.screen.ChromeCastDevice

@Composable
internal fun DeviceItem(
    device: ChromeCastDevice,
    icon: @Composable (() -> Unit),
    loading: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    ListItem(modifier = Modifier.clickable { onClick?.invoke() },
        headlineContent = {
            Text(text = device.name ?: "Unknown")
        },
        overlineContent = {
            Text(text = device.description ?: "Unknown")
        },
        leadingContent = {
            icon()
        },
        trailingContent = {
            if (loading)
                CircularProgressIndicator()
        }
    )
}