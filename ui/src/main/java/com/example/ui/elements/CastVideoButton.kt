package com.example.ui.elements

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.R

@Composable
internal fun CastVideoButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick, modifier = modifier
            .fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 14.dp)
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}

@Preview
@Composable
private fun CastVideoButtonPreview() {
    CastVideoButton(text = stringResource(id = R.string.cast_video))
}