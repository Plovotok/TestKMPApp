package ru.plovotok.testkmpapp.presentation.ui.components.top_bar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isFinite
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastFirst
import ru.plovotok.testkmpapp.presentation.ui.theme.colorScheme
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * Базовый компонент CollapsingTopBar
 * @see CollapsingTopBarConstants
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksCollapsingTopBar(
    title: String,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    titleBlock: @Composable () -> Unit = {
        Text(
            text = title,
            fontSize = 18.sp,
            color = colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            softWrap = true,
            overflow = TextOverflow.Ellipsis,
        )
    },
    shouldUseNavigationIconMinSize: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
    showTopTitle: Boolean = remember {
        derivedStateOf { scrollBehavior.state.collapsedFraction >= 1.0f }
    }.value,
    titleHorizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    modifier: Modifier = Modifier
) {

    val topTitleAlpha by animateFloatAsState(
        if (showTopTitle) 1f else 0f,
    )
    Column(
        modifier
            .clickable(
                onClick = {},
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .fillMaxWidth()
            .windowInsetsPadding(TopAppBarDefaults.windowInsets)
    ) {

        TwoRowsTopAppBar(
            title = {
                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    text = title,
                    maxLines = 1,
                    style = TextStyle(fontWeight = FontWeight.SemiBold),
                    autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, maxFontSize = 36.sp, stepSize = 1.sp),
                    overflow = TextOverflow.Ellipsis
                )
            },
            titleHorizontalArrangement = titleHorizontalArrangement,
            actions = actions,
            navigationIcon = {
                if (navigationIcon != null) {
                    Box(
                        modifier = Modifier.defaultMinSize(minWidth = if (shouldUseNavigationIconMinSize) 48.dp else 0.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        navigationIcon()
                    }
                } else {
                    Box(modifier = Modifier.width(48.dp))
                }
            },
            showTopTitle = showTopTitle,
            titleBottomPadding = 0.dp,
            smallTitle = {
                Box(
                    modifier
                        .graphicsLayer {
                            this.alpha = topTitleAlpha
                        }
                ) {
                    titleBlock()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            collapsedHeight = CollapsingTopBarConstants.CollapsedHeight,
            expandedHeight = CollapsingTopBarConstants.ExpandedHeight,
            windowInsets = WindowInsets(0.dp),
            scrollBehavior = scrollBehavior
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
suspend fun TopAppBarState.collapse() = Animatable(heightOffset).animateTo(heightOffsetLimit) {
    heightOffset = this.value
}

@OptIn(ExperimentalMaterial3Api::class)
suspend fun TopAppBarState.expand() = Animatable(heightOffset).animateTo(0f) {
    heightOffset = this.value
}


@Immutable
object CollapsingTopBarConstants {
    val TopAppBarHorizontalPadding = 4.dp
    val ContentVerticalPadding = 12.dp
    val TopBarBottomPadding = 8.dp
    val TopAppBarTitleInset = 16.dp - TopAppBarHorizontalPadding

    val ExpandedHeight = 124.dp
    val CollapsedHeight = 60.dp
}

internal fun interface ScrolledOffset {
    fun offset(): Float
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TwoRowsTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    showTopTitle: Boolean,
    titleBottomPadding: Dp,
    titleHorizontalArrangement: Arrangement.Horizontal,
    smallTitle: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    collapsedHeight: Dp,
    expandedHeight: Dp,
    windowInsets: WindowInsets,
    scrollBehavior: TopAppBarScrollBehavior?,
) {
    require(collapsedHeight.isSpecified && collapsedHeight.isFinite) {
        "The collapsedHeight is expected to be specified and finite"
    }
    require(expandedHeight.isSpecified && expandedHeight.isFinite) {
        "The expandedHeight is expected to be specified and finite"
    }
    require(expandedHeight >= collapsedHeight) {
        "The expandedHeight is expected to be greater or equal to the collapsedHeight"
    }
    val expandedHeightPx: Float
    val collapsedHeightPx: Float
    val titleBottomPaddingPx: Int
    LocalDensity.current.run {
        expandedHeightPx = expandedHeight.toPx()
        collapsedHeightPx = collapsedHeight.toPx()
        titleBottomPaddingPx = titleBottomPadding.roundToPx()
    }

    // Sets the app bar's height offset limit to hide just the bottom title area and keep top title
    // visible when collapsed.
    SideEffect {
        if (scrollBehavior?.state?.heightOffsetLimit != collapsedHeightPx - expandedHeightPx) {
            scrollBehavior?.state?.heightOffsetLimit = collapsedHeightPx - expandedHeightPx
        }
    }

    // Wrap the given actions in a Row.
    val actionsRow =
        @Composable {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        }
    val topTitleAlpha by animateFloatAsState(
        if (showTopTitle) 1f else 0f,
    )

    // Set up support for resizing the top app bar when vertically dragging the bar itself.
//    val appBarDragModifier =
//        if (scrollBehavior != null && !scrollBehavior.isPinned) {
//            Modifier.draggable(
//                orientation = Orientation.Vertical,
//                state =
//                rememberDraggableState { delta -> scrollBehavior.state.heightOffset += delta },
//                onDragStopped = { velocity ->
//                    settleAppBar(
//                        scrollBehavior.state,
//                        velocity,
//                        scrollBehavior.flingAnimationSpec,
//                        scrollBehavior.snapAnimationSpec
//                    )
//                }
//            )
//        } else {
//            Modifier
//        }


    Column(
        modifier = modifier
    ) {
        TopAppBarLayout(
            modifier =
            Modifier
                .windowInsetsPadding(windowInsets)
                // clip after padding so we don't show the title over the inset area
                .clipToBounds()
                .heightIn(max = collapsedHeight),
            scrolledOffset = { 0f },
            title = smallTitle,
            titleAlpha = { topTitleAlpha },
            titleVerticalArrangement = Arrangement.Center,
            titleHorizontalArrangement = titleHorizontalArrangement,
            titleBottomPadding = 0,
            navigationIcon = navigationIcon,
            actions = actionsRow,
        )
        TopAppBarLayout(
            modifier =
            Modifier
                // only apply the horizontal sides of the window insets padding, since the
                // top
                // padding will always be applied by the layout above
                .windowInsetsPadding(windowInsets.only(WindowInsetsSides.Horizontal))
                .clipToBounds()
                .heightIn(max = expandedHeight - collapsedHeight),
            scrolledOffset = { scrollBehavior?.state?.heightOffset ?: 0f },
            title = title,
            titleAlpha = { 1f },
            titleVerticalArrangement = Arrangement.Bottom,
            titleHorizontalArrangement = Arrangement.Start,
            titleBottomPadding = titleBottomPaddingPx,
            navigationIcon = {},
            actions = {}
        )
    }
}


@Composable
internal fun TopAppBarLayout(
    modifier: Modifier,
    scrolledOffset: ScrolledOffset,
    title: @Composable () -> Unit,
    titleAlpha: () -> Float,
    titleVerticalArrangement: Arrangement.Vertical,
    titleHorizontalArrangement: Arrangement.Horizontal,
    titleBottomPadding: Int,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
) {
    Layout(
        {
            Box(
                Modifier
                    .layoutId("navigationIcon")
                    .padding(start = CollapsingTopBarConstants.TopAppBarHorizontalPadding)
                    .padding(bottom = CollapsingTopBarConstants.ContentVerticalPadding)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides colorScheme.primary,
                    content = navigationIcon
                )
            }
            Box(
                Modifier
                    .layoutId("title")
                    .padding(horizontal = CollapsingTopBarConstants.TopAppBarHorizontalPadding)
                    .padding(bottom = CollapsingTopBarConstants.ContentVerticalPadding)
                    .graphicsLayer(alpha = titleAlpha())
            ) {
                title()
            }
            Row (
                Modifier
                    .layoutId("actionIcons")
                    .padding(end = CollapsingTopBarConstants.TopAppBarHorizontalPadding)
                    .padding(bottom = CollapsingTopBarConstants.ContentVerticalPadding)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides colorScheme.primary,
                    content = actions
                )
            }
        },
        modifier = modifier
    ) { measurables, constraints ->
        val navigationIconPlaceable =
            measurables
                .fastFirst { it.layoutId == "navigationIcon" }
                .measure(constraints.copy(minWidth = 0))
        val actionIconsPlaceable =
            measurables
                .fastFirst { it.layoutId == "actionIcons" }
                .measure(constraints.copy(minWidth = 0))

        val maxTitleWidth =
            if (constraints.maxWidth == Constraints.Infinity) {
                constraints.maxWidth - 16.dp.roundToPx()
            } else {
                (constraints.maxWidth - navigationIconPlaceable.width - actionIconsPlaceable.width - 16.dp.roundToPx())
                    .coerceAtLeast(0)
            }
        val titlePlaceable =
            measurables
                .fastFirst { it.layoutId == "title" }
                .measure(constraints.copy(minWidth = 0, maxWidth = maxTitleWidth))

        // Locate the title's baseline.
        val titleBaseline =
            if (titlePlaceable[LastBaseline] != AlignmentLine.Unspecified) {
                titlePlaceable[LastBaseline]
            } else {
                0
            }

        // Subtract the scrolledOffset from the maxHeight. The scrolledOffset is expected to be
        // equal or smaller than zero.
        val scrolledOffsetValue = scrolledOffset.offset()
        val heightOffset = if (scrolledOffsetValue.isNaN()) 0 else scrolledOffsetValue.roundToInt()

        val layoutHeight =
            if (constraints.maxHeight == Constraints.Infinity) {
                constraints.maxHeight
            } else {
                constraints.maxHeight + heightOffset
            }

        layout(constraints.maxWidth, layoutHeight.coerceAtLeast(0)) {
            // Navigation icon
            navigationIconPlaceable.placeRelative(
                x = 0,
                y = (layoutHeight - navigationIconPlaceable.height) / 2
            )

            // Title
            titlePlaceable.placeRelative(
                x =
                when (titleHorizontalArrangement) {
                    Arrangement.Center -> {
                        var baseX = (constraints.maxWidth - titlePlaceable.width) / 2
                        if (baseX < navigationIconPlaceable.width) {
                            // May happen if the navigation is wider than the actions and the
                            // title is long. In this case, prioritize showing more of the title
                            // by
                            // offsetting it to the right.
                            baseX += (navigationIconPlaceable.width - baseX)
                        } else if (
                            baseX + titlePlaceable.width >
                            constraints.maxWidth - actionIconsPlaceable.width
                        ) {
                            // May happen if the actions are wider than the navigation and the
                            // title
                            // is long. In this case, offset to the left.
                            baseX +=
                                ((constraints.maxWidth - actionIconsPlaceable.width) -
                                        (baseX + titlePlaceable.width))
                        }
                        baseX
                    }

                    Arrangement.End ->
                        constraints.maxWidth - titlePlaceable.width - actionIconsPlaceable.width
                    // Arrangement.Start.
                    // An TopAppBarTitleInset will make sure the title is offset in case the
                    // navigation icon is missing.
                    else -> max(
                        CollapsingTopBarConstants.TopAppBarTitleInset.roundToPx(),
                        navigationIconPlaceable.width
                    )
                },
                y =
                when (titleVerticalArrangement) {
                    Arrangement.Center -> (layoutHeight - titlePlaceable.height) / 2
                    // Apply bottom padding from the title's baseline only when the Arrangement
                    // is
                    // "Bottom".
                    Arrangement.Bottom ->
                        if (titleBottomPadding == 0) {
                            layoutHeight - titlePlaceable.height
                        } else {
                            // Calculate the actual padding from the bottom of the title, taking
                            // into account its baseline.
                            val paddingFromBottom =
                                titleBottomPadding - (titlePlaceable.height - titleBaseline)
                            // Adjust the bottom padding to a smaller number if there is no room
                            // to
                            // fit the title.
                            val heightWithPadding = paddingFromBottom + titlePlaceable.height
                            val adjustedBottomPadding =
                                if (heightWithPadding > constraints.maxHeight) {
                                    paddingFromBottom -
                                            (heightWithPadding - constraints.maxHeight)
                                } else {
                                    paddingFromBottom
                                }

                            layoutHeight - titlePlaceable.height - max(0, adjustedBottomPadding)
                        }
                    // Arrangement.Top
                    else -> 0
                }
            )

            // Action icons
            actionIconsPlaceable.placeRelative(
                x = constraints.maxWidth - actionIconsPlaceable.width,
                y = (layoutHeight - actionIconsPlaceable.height) / 2
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
suspend fun settleAppBar(
    state: TopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?,
): Velocity {
    // Check if the app bar is completely collapsed/expanded. If so, no need to settle the app bar,
    // and just return Zero Velocity.
    // Note that we don't check for 0f due to float precision with the collapsedFraction
    // calculation.
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainingVelocity = velocity
    // In case there is an initial velocity that was left after a previous user fling, animate to
    // continue the motion to expand or collapse the app bar.
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainingVelocity = this.velocity
                // avoid rounding errors and stop if anything is unconsumed
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
    }
    // Snap if animation specs were provided.
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 && state.heightOffset > state.heightOffsetLimit) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
                animationSpec = snapAnimationSpec
            ) {
                state.heightOffset = value
            }
        }
    }

    return Velocity(0f, remainingVelocity)
}