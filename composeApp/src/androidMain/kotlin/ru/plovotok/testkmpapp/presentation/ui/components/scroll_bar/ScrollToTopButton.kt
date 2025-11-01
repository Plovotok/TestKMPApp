package ru.plovotok.testkmpapp.presentation.ui.components.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.plovotok.testkmpapp.presentation.ui.colorScheme
import ru.plovotok.testkmpapp.presentation.ui.theme.BooksTheme
import kotlin.math.absoluteValue

private val defaultThreshold = 100.dp

@Composable
private fun rememberDirectionalLazyListState(
    lazyListState: LazyListState,
    scrollThreshold: Dp = defaultThreshold,
): DirectionalLazyListState {
    val threshold = with (LocalDensity.current) { scrollThreshold.roundToPx()}
    return remember(lazyListState) {
        DirectionalLazyListState(lazyListState, threshold)
    }
}

@Composable
private fun rememberDirectionalLazyGridState(
    lazyGridState: LazyGridState,
    scrollThreshold: Dp = defaultThreshold,
): DirectionalLazyGridState {
    val threshold = with (LocalDensity.current) { scrollThreshold.roundToPx()}
    return remember(lazyGridState) {
        DirectionalLazyGridState(lazyGridState, threshold)
    }
}

@Composable
private fun rememberDirectionalScrollState(
    scrollState: ScrollState,
    scrollThreshold: Dp = defaultThreshold,
): DirectionalScrollState {
    val threshold = with (LocalDensity.current) { scrollThreshold.roundToPx()}
    return remember(scrollState) {
        DirectionalScrollState(scrollState, threshold)
    }
}

private class DirectionalScrollState(
    private val scrollState: ScrollState,
    private val threshold: Int
) {
    private var positionY = scrollState.value

    private var accDelta = 0

    val scrollDirection by derivedStateOf {
        val newPositionY = scrollState.value
        if (scrollState.canScrollBackward) {
            val delta = newPositionY - positionY
            if (newPositionY < positionY) {
                accDelta += delta
                if (accDelta.absoluteValue >= threshold) {
                    positionY = newPositionY
                    ScrollDirection.Up
                } else {
                    positionY = newPositionY
                    ScrollDirection.NONE
                }
            } else {
                accDelta = 0
                positionY = newPositionY
                ScrollDirection.Down
            }
        } else {
            accDelta = 0
            positionY = 0
            ScrollDirection.NONE
        }
    }
}

private class DirectionalLazyListState(
    private val lazyListState: LazyListState,
    private val threshold: Int
) {
    private var positionY = lazyListState.firstVisibleItemScrollOffset
    private var visibleItem = lazyListState.firstVisibleItemIndex

    private var accDelta = 0

    val scrollDirection by derivedStateOf {
        val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex
        val firstVisibleItemScrollOffset =
            lazyListState.firstVisibleItemScrollOffset

        if (lazyListState.canScrollBackward) {
            // We are scrolling while the first visible item hasn't changed yet
            if (firstVisibleItemIndex == visibleItem) {
                val delta = firstVisibleItemScrollOffset - positionY
                accDelta += delta
                val direction = if (firstVisibleItemScrollOffset >= positionY) {
                    accDelta = 0
                    ScrollDirection.Down
                } else {
                    if (accDelta.absoluteValue >= threshold) {
                        ScrollDirection.Up
                    } else {
                        ScrollDirection.NONE
                    }
                }
                positionY = firstVisibleItemScrollOffset

                direction
            } else {

                val direction = if (firstVisibleItemIndex >= visibleItem) {
                    accDelta = 0
                    ScrollDirection.Down
                } else {
                    ScrollDirection.Up
                }
                positionY = firstVisibleItemScrollOffset
                visibleItem = firstVisibleItemIndex
                direction
            }
        } else {
            accDelta = 0
            ScrollDirection.NONE
        }
    }
}

private class DirectionalLazyGridState(
    private val lazyGridState: LazyGridState,
    private val threshold: Int
) {
    private var positionY = lazyGridState.firstVisibleItemScrollOffset
    private var visibleItem = lazyGridState.firstVisibleItemIndex

    private var accDelta = 0

    val scrollDirection by derivedStateOf {
        val firstVisibleItemIndex = lazyGridState.firstVisibleItemIndex
        val firstVisibleItemScrollOffset =
            lazyGridState.firstVisibleItemScrollOffset

        if (lazyGridState.canScrollBackward) {
            // We are scrolling while the first visible item hasn't changed yet
            if (firstVisibleItemIndex == visibleItem) {
                val delta = firstVisibleItemScrollOffset - positionY
                accDelta += delta
                val direction = if (firstVisibleItemScrollOffset >= positionY) {
                    accDelta = 0
                    ScrollDirection.Down
                } else {
                    if (accDelta.absoluteValue >= threshold) {
                        ScrollDirection.Up
                    } else {
                        ScrollDirection.NONE
                    }
                }
                positionY = firstVisibleItemScrollOffset

                direction
            } else {

                val direction = if (firstVisibleItemIndex >= visibleItem) {
                    accDelta = 0
                    ScrollDirection.Down
                } else {
                    ScrollDirection.Up
                }
                positionY = firstVisibleItemScrollOffset
                visibleItem = firstVisibleItemIndex
                direction
            }
        } else {
            accDelta = 0
            ScrollDirection.NONE
        }
    }
}

enum class ScrollDirection {
    Up, Down, NONE
}

@Composable
private fun calculateMinScrollSize(): Float {
    val windowInfo = LocalWindowInfo.current
    return remember(windowInfo) {
        (windowInfo.containerSize.height * 1.7f)
    }
}

private val DefaultScrollSpeed = 800.dp / 1000
private const val MaxScrollDuration = 300f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.ScrollToTopButton(
    scrollState: ScrollState,
    verticalPadding: Dp = 16.dp,
    horizontalPadding: Dp = 16.dp,
    alignment: Alignment = Alignment.BottomEnd,
    scrollThreshold: Dp = defaultThreshold,
    animationSpec: Density.() -> AnimationSpec<Float> = {
        val animationDuration = (scrollState.value / DefaultScrollSpeed.toPx()).coerceIn(minimumValue = 1f, maximumValue = MaxScrollDuration)
        tween(durationMillis = animationDuration.toInt(), easing = LinearEasing)
    }
) {
    val scope = rememberCoroutineScope()

    val directionalScrollState = rememberDirectionalScrollState(scrollState, scrollThreshold)

    val showButton = directionalScrollState.scrollDirection == ScrollDirection.Up

    val minScrollSize = calculateMinScrollSize()

    val isSufficientHeight by remember(scrollState) {
        derivedStateOf {
            val totalHeight = scrollState.maxValue
            totalHeight > minScrollSize
        }
    }

    val density = LocalDensity.current

    ScrollToTopButton(
        onClick = {
            scope.launch {
                scrollState.animateScrollTo(0, animationSpec = animationSpec(density))
            }
        },
        verticalPadding = verticalPadding,
        horizontalPadding = horizontalPadding,
        alignment = alignment,
        showButton = showButton && isSufficientHeight,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.ScrollToTopButton(
    scrollState: LazyListState,
    verticalPadding: Dp = 16.dp,
    horizontalPadding: Dp = 16.dp,
    alignment: Alignment = Alignment.BottomEnd,
    scrollThreshold: Dp = defaultThreshold,
) {
    val scope = rememberCoroutineScope()

    val directionalState = rememberDirectionalLazyListState(scrollState, scrollThreshold)

    val showButton = directionalState.scrollDirection == ScrollDirection.Up

    val minScrollSize = calculateMinScrollSize()

    val isSufficientHeight by remember(scrollState) {
        derivedStateOf {
            val visibleItemsInfo = scrollState.layoutInfo.visibleItemsInfo
            val totalItems = scrollState.layoutInfo.totalItemsCount
            if (visibleItemsInfo.isEmpty() || totalItems == 0) return@derivedStateOf false

            val allItemsHeight = with (scrollState.layoutInfo.visibleItemsInfo) {
                sumOf { it.size } / size * totalItems
            }

            allItemsHeight > minScrollSize
        }
    }

    ScrollToTopButton(
        onClick = {
            scope.launch {
                scrollState.animateScrollToItem(0)
            }
        },
        verticalPadding = verticalPadding,
        horizontalPadding = horizontalPadding,
        alignment = alignment,
        showButton = showButton && isSufficientHeight,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.ScrollToTopButton(
    scrollState: LazyGridState,
    verticalPadding: Dp = 16.dp,
    horizontalPadding: Dp = 16.dp,
    alignment: Alignment = Alignment.BottomEnd,
    scrollThreshold: Dp = defaultThreshold,
) {
    val scope = rememberCoroutineScope()
    val directionalState = rememberDirectionalLazyGridState(scrollState, scrollThreshold)
    val showButton = directionalState.scrollDirection == ScrollDirection.Up

    val minScrollSize = calculateMinScrollSize()
    val isSufficientHeight by remember(scrollState) {
        derivedStateOf {
            val visibleItemsInfo = scrollState.layoutInfo.visibleItemsInfo
            val totalItems = scrollState.layoutInfo.totalItemsCount
            if (visibleItemsInfo.isEmpty() || totalItems == 0) return@derivedStateOf false

            val allItemsHeight = with (scrollState.layoutInfo.visibleItemsInfo) {
                sumOf { it.size.height } / size * totalItems
            }

            allItemsHeight > minScrollSize
        }
    }

    ScrollToTopButton(
        onClick = {
            scope.launch {
                scrollState.animateScrollToItem(0)
            }
        },
        verticalPadding = verticalPadding,
        horizontalPadding = horizontalPadding,
        alignment = alignment,
        showButton = showButton && isSufficientHeight,
    )
}

@Composable
private fun BoxScope.ScrollToTopButton(
    onClick: () -> Unit,
    showButton: Boolean,
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 16.dp,
    horizontalPadding: Dp = 16.dp,
    alignment: Alignment = Alignment.BottomEnd,
) {
    AnimatedVisibility(
        visible = showButton,
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it },
        modifier = modifier.align(alignment)
    ) {
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = horizontalPadding)
                .padding(vertical = verticalPadding)
                .size(56.dp)
                .dropShadow(
                    CircleShape,
                    Shadow(
                        16.dp,
                        alpha = 0.28f
                    )
                )
                .clip(RoundedCornerShape(50))
                .background(colorScheme.surface2)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                tint = colorScheme.primary,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer {
                        rotationZ = -90f
                    },
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun ScrollToTopButtonPreview() {
    BooksTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Content",
                )
            }
            ScrollToTopButton(
                onClick = {

                },
                showButton = true
            )
        }
    }
}