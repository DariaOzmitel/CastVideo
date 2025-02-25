package com.example.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.elements.CastVideoButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun CastVideoScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onButtonClickListener: () -> Unit,
) {
    val viewModel: CastVideoScreenViewModel = koinViewModel()
    CastVideoScreenContent(
        modifier = modifier,
        innerPadding = innerPadding
    ) {
        onButtonClickListener()
    }
}

@Composable
private fun CastVideoScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onButtonClickListener: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize().background(color = Color.Black)
            .padding(
            top = innerPadding.calculateTopPadding() + 32.dp,
            bottom = innerPadding.calculateBottomPadding(),
            start = 16.dp,
            end = 16.dp
        )
            , verticalArrangement = Arrangement.Center

    ) {
        CastVideoButton(text = stringResource(R.string.cast_video)) {
            onButtonClickListener()
        }
    }
}

@Preview
@Composable
private fun CastVideoScreenPreview() {
    CastVideoScreenContent(innerPadding = PaddingValues(0.dp)) {}
}