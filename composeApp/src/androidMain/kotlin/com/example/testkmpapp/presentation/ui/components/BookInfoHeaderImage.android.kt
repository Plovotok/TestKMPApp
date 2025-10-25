package com.example.testkmpapp.presentation.ui.components

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.toBitmap
import com.kmpalette.rememberDominantColorState
import com.kmpalette.rememberPaletteState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
actual fun BookInfoHeaderImage(
    modifier: Modifier,
    imageUrl: String?,
    onLightChange: (isLight: Boolean) -> Unit,
    contentDescription: String?,
) {

    var scale by remember {
        mutableFloatStateOf(1f)
    }
    val density = LocalDensity.current


    val paletteState = rememberDominantColorState(
        defaultColor = MaterialTheme.colorScheme.background
    )

    val color = paletteState.color

    LaunchedEffect(color) {
        onLightChange(color.isLightColor())
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .onGloballyPositioned {
                with(density) {
                    scale = 1f + (it.positionInWindow().y / 200.dp.toPx())
                }
            }
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AsyncImage(
                model = imageUrl,
                contentDescription = contentDescription,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = (scale + 0.1f).coerceAtLeast(1.1f)
                        scaleY = (scale + 0.1f).coerceAtLeast(1.1f)
                        this.transformOrigin = TransformOrigin(0.5f, 1f)
                    }
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .blur(radius = 20.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = (scale + 0.1f).coerceAtLeast(1.1f)
                        scaleY = (scale + 0.1f).coerceAtLeast(1.1f)
                        this.transformOrigin = TransformOrigin(0.5f, 1f)
                    }
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .background(color)
            )
        }

        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            onSuccess = {
                scope.launch {
                    withContext(Dispatchers.IO) {
                        val tmp = it.result.image.toBitmap()
                        val bmp = tmp.copy(Bitmap.Config.ARGB_8888, false)
                        val image = bmp.asImageBitmap()
                        paletteState.updateFrom(image)
                    }

                }
            },
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale.coerceAtLeast(1f)
                    scaleY = scale.coerceAtLeast(1f)
                    this.transformOrigin = TransformOrigin(0.5f, 1f)
                }
                .fillMaxWidth()
                .aspectRatio(1.5f)
        )
    }
}