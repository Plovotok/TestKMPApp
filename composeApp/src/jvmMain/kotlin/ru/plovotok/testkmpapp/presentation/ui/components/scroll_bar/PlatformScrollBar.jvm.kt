package ru.plovotok.testkmpapp.presentation.ui.components.scroll_bar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import ru.plovotok.testkmpapp.presentation.ui.theme.colorScheme

@Composable
actual fun BoxScope.PlatformScrollController(
    listState: LazyListState,
    reverseLayout: Boolean,
    modifier: Modifier,
    topPadding: Dp,
    bottomPadding: Dp
) {

    val adapter = rememberScrollbarAdapter(listState)

    val style = LocalScrollbarStyle.current.copy(
        unhoverColor = colorScheme.onBackground.copy(alpha = 0.27f),
        hoverColor = colorScheme.onBackground.copy(alpha = 0.60f)
    )

    val interactionSource = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()
    val isDragged by interactionSource.collectIsDraggedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val alpha by animateFloatAsState(
        targetValue = if (listState.isScrollInProgress || isDragged || isPressed || isHovered) 1f else 0f,
        animationSpec = tween(250, delayMillis = if (listState.isScrollInProgress) 0 else 700)
    )

    VerticalScrollbar(
        adapter = adapter,
        reverseLayout = reverseLayout,
        style = style,
        interactionSource = interactionSource,
        modifier = modifier
            .padding(
                top = topPadding,
                bottom = bottomPadding
            )
            .fillMaxHeight()
            .align(Alignment.CenterEnd)
            .graphicsLayer {
                this.alpha = alpha
            }
    )
}