package ru.plovotok.testkmpapp.presentation.ui.components

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Expand
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanels
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanelsAnimators
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimationScope
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import ru.plovotok.testkmpapp.presentation.iosLikeSlideExperimental
import ru.plovotok.testkmpapp.presentation.ui.LocalResizeIcon
import ru.plovotok.testkmpapp.presentation.ui.colorScheme
import ru.plovotok.testkmpapp.presentation.ui.components.panel.DynamicWidthChildPanelLayout

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun <MC : Any, MT : Any, DC : Any, DT : Any, EC : Any, ET : Any> DynamicWeightChildPanels(
    currentLeftWeight: () -> Float,
    onWeightChange: (Float) -> Unit,
    panels: ChildPanels<MC, MT, DC, DT, EC, ET>,
    mainChild: @Composable StackAnimationScope.(Child.Created<MC, MT>) -> Unit,
    detailsChild: @Composable StackAnimationScope.(Child.Created<DC, DT>) -> Unit,
    component: ChildPanelsComponent,
    modifier: Modifier = Modifier,
    secondPanelPlaceholder: @Composable StackAnimationScope.() -> Unit = {},
    predictiveBackParams: (ChildPanels<MC, MT, DC, DT, EC, ET>) -> PredictiveBackParams? = { null },
) {
    val dividerColor = colorScheme.lightGrayTinted

    BoxWithConstraints(modifier = modifier) {

        val fullWidthPx = with (LocalDensity.current) { maxWidth.roundToPx() }
        val fullHeightPx = with (LocalDensity.current) { maxHeight.roundToPx() }

        val mode =
            when {
                maxWidth >= 800.dp -> ChildPanelsMode.DUAL
                else -> ChildPanelsMode.SINGLE
            }

        val availableInterval = remember {
            0.3f..0.65f
        }

        DisposableEffect(mode) {
            component.setMode(mode)
            onDispose {}
        }

        val draggableState = rememberDraggableState {
            val newDelta = it / fullWidthPx
            onWeightChange((currentLeftWeight() + newDelta).coerceIn(availableInterval))
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
                    Box(
                        modifier = Modifier
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
                    ) {
                        this@ChildPanels.mainChild(it)
                    }
                },
                detailsChild = detailsChild,
                extraChild = {},
                layout = DynamicWidthChildPanelLayout(
                    currentWeight = currentLeftWeight
                ),
                secondPanelPlaceholder = secondPanelPlaceholder,
                animators = ChildPanelsAnimators(
                    single = iosLikeSlideExperimental(),
                    dual = fade() to fade()
                ),
                predictiveBackParams = predictiveBackParams,
            )

            val hoverIcon = LocalResizeIcon.current
            if (mode != ChildPanelsMode.SINGLE) {
                if (hoverIcon != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(20.dp)
                            .graphicsLayer {
                                translationX = fullWidthPx * currentLeftWeight()
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
                                translationX = fullWidthPx * currentLeftWeight() - size.width / 2
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

interface ChildPanelsComponent {

    @OptIn(ExperimentalDecomposeApi::class)
    fun setMode(mode: ChildPanelsMode)
}
