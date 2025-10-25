package com.example.testkmpapp.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
expect fun BookInfoHeaderImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    onLightChange: (isLight: Boolean) -> Unit,
    contentDescription: String?,
)

fun Color.isLightColor(): Boolean {
    val luminance = 0.299 * red + 0.587 * green + 0.114 * blue
    return luminance > 0.5
}