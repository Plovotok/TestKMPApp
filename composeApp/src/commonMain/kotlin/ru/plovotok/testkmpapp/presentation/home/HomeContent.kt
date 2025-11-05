package ru.plovotok.testkmpapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Expand
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanels
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanelsAnimators
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import ru.plovotok.testkmpapp.presentation.getPredictiveBackAnimatable
import ru.plovotok.testkmpapp.presentation.info.BookInfoContent
import ru.plovotok.testkmpapp.presentation.iosLikeSlideExperimental
import ru.plovotok.testkmpapp.presentation.ui.BaseScreen
import ru.plovotok.testkmpapp.presentation.ui.LocalResizeIcon
import ru.plovotok.testkmpapp.presentation.ui.colorScheme
import ru.plovotok.testkmpapp.presentation.ui.components.panel.DynamicWidthChildPanelLayout
import ru.plovotok.testkmpapp.presentation.ui.components.screens.EmptyScreen
import kotlin.with

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun HomeContent(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {

    val panels by component.panels.subscribeAsState()

    val activeBookId = panels.details?.instance?.preview?.id

    val dividerColor = colorScheme.lightGrayTinted

    var deltaWidth: Int by rememberSaveable {
        mutableStateOf(0)
    }

    BaseScreen(
        contentWindowInsets = WindowInsets(0.dp)
    ) {

        BoxWithConstraints(modifier = modifier.fillMaxSize()) {
            val fullWidthPx = with (LocalDensity.current) { maxWidth.roundToPx() }
            val fullHeightPx = with (LocalDensity.current) { maxHeight.roundToPx() }
            val mode =
                when {
                    maxWidth >= 800.dp -> ChildPanelsMode.DUAL
                    else -> ChildPanelsMode.SINGLE
                }

            val availableInterval by rememberUpdatedState(with (LocalDensity.current) {
                (fullWidthPx * 0.3f)..(fullWidthPx * 0.65f)
            })

            LaunchedEffect(availableInterval) {
                val newDelta = deltaWidth

                val width = fullWidthPx * 0.45f - newDelta
                if (width !in availableInterval) {
                    val min = availableInterval.start
                    val max = availableInterval.endInclusive

                    if (width > max) {
                        deltaWidth = (width - max).toInt()
                    } else {
                        deltaWidth = (width - min).toInt()
                    }
                }
            }

            DisposableEffect(mode) {
                component.setMode(mode)
                onDispose {}
            }

            val draggableState = rememberDraggableState {
                val newDelta = deltaWidth - it.toInt()
                if ((fullWidthPx * 0.45f - newDelta) in availableInterval) {
                    deltaWidth = newDelta
                }
            }

            val iconInteractionSource = remember{ MutableInteractionSource() }

            val isHovered by iconInteractionSource.collectIsHoveredAsState()
            val isDragged by iconInteractionSource.collectIsDraggedAsState()
            val isPressed by iconInteractionSource.collectIsPressedAsState()

            val iconAlpha = if (isHovered || isPressed ||isDragged) 0.8f else 0.4f

            Box {
                ChildPanels(
                    panels = panels,
                    mainChild = {
                        BookListContent(
                            component = it.instance,
                            activeBookId = activeBookId,
                            modifier = Modifier
                                .fillMaxSize()
                                .drawWithContent {
                                    drawContent()
                                    if (mode != ChildPanelsMode.SINGLE) {
                                        drawLine(
                                            color = dividerColor,
                                            start = Offset(size.width, 0f),
                                            end = Offset(size.width, size.height)
                                        )
                                    }
                                }
                        )
                    },
                    detailsChild = {
                        BookInfoContent(
                            component = it.instance,
                            showBackButton = mode == ChildPanelsMode.SINGLE,
                            modifier = Modifier.fillMaxSize()
                        )
                    },
                    extraChild = {

                    },
                    layout = DynamicWidthChildPanelLayout(
                        startWeight = 0.45f,
                        getLeftWidth = { deltaWidth },
                    ),
                    secondPanelPlaceholder = {
                        EmptyScreen(title = "Choose book from list")
                    },
                    animators = ChildPanelsAnimators(
                        single = iosLikeSlideExperimental(),
                        dual = fade() to fade()
                    ),
                    predictiveBackParams = {
                        PredictiveBackParams(
                            backHandler = component.backHandler,
                            onBack = component::onBack,
                            animatable = ::getPredictiveBackAnimatable,
                        )
                    },
                )

                val hoverIcon = LocalResizeIcon.current
                if (mode != ChildPanelsMode.SINGLE) {
                    if (hoverIcon != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(20.dp)
                                .graphicsLayer {
                                    translationX = fullWidthPx * 0.45f - deltaWidth - size.width / 2
                                    translationY = fullHeightPx / 2 - size.height / 2
                                }
                                .draggable(
                                    state = draggableState,
                                    orientation = Orientation.Horizontal,
                                    interactionSource = iconInteractionSource
                                )
                                .pointerHoverIcon(hoverIcon),
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .graphicsLayer {
                                    translationX = fullWidthPx * 0.45f - deltaWidth - size.width / 2
                                    translationY = fullHeightPx / 2 - size.height / 2
                                }
                                .background(
                                    colorScheme.lightGrayTinted.copy(alpha = iconAlpha),
                                    CircleShape
                                )
                                .draggable(
                                    state = draggableState,
                                    orientation = Orientation.Horizontal,
                                    interactionSource = iconInteractionSource
                                )
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Expand,
                                contentDescription = "Change width",
                                modifier = Modifier
                                    .rotate(90f)
                            )
                        }
                    }
                }
            }
        }
    }
}