package ru.plovotok.testkmpapp.presentation.ui.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.plovotok.testkmpapp.presentation.ui.components.isLightColor
import com.kmpalette.loader.rememberPainterLoader
import com.kmpalette.rememberDominantColorState
import kotlinx.coroutines.launch

@Composable
actual fun BookInfoHeaderImage(
    modifier: Modifier,
    imageUrl: String?,
    onLightChange: (isLight: Boolean) -> Unit,
    contentDescription: String?
) {

    var scale by remember {
        mutableFloatStateOf(1f)
    }
    val density = LocalDensity.current

    val loader = rememberPainterLoader()

    val dominantColorState = rememberDominantColorState(loader = loader)

    val color = dominantColorState.color

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

        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            onSuccess = {
                scope.launch {
                    loader.load(it.painter)
                    dominantColorState.updateFrom(it.painter)
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