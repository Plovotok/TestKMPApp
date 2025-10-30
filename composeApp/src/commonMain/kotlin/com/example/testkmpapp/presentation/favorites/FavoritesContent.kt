package com.example.testkmpapp.presentation.favorites

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanels
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanelsAnimators
import com.arkivanov.decompose.extensions.compose.experimental.panels.HorizontalChildPanelsLayout
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.example.testkmpapp.presentation.getPredictiveBackAnimatable
import com.example.testkmpapp.presentation.info.BookInfoContent
import com.example.testkmpapp.presentation.iosLikeSlideExperimental
import com.example.testkmpapp.presentation.ui.BaseScreen
import com.example.testkmpapp.presentation.ui.colorScheme
import com.example.testkmpapp.presentation.ui.components.screens.EmptyScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun FavoritesContent(
    component: FavoritesComponent,
    modifier: Modifier = Modifier
) {
    val panels by component.panels.subscribeAsState()

    val activeBookId = panels.details?.instance?.preview?.id

    val dividerColor = colorScheme.lightGrayTinted

    BaseScreen(
        contentWindowInsets = WindowInsets(0.dp)
    ) {
        BoxWithConstraints(modifier = modifier.fillMaxSize()) {

            val mode =
                when {
                    maxWidth >= 800.dp -> ChildPanelsMode.DUAL
                    else -> ChildPanelsMode.SINGLE
                }

            DisposableEffect(mode) {
                component.setMode(mode)
                onDispose {}
            }

            ChildPanels(
                panels = panels,
                mainChild = {
                    FavoritesListContent(
                        component = it.instance,
                        activeBookId = activeBookId,
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithContent {
                                drawContent()
                                drawLine(
                                    color = dividerColor,
                                    start = Offset(size.width, 0f),
                                    end = Offset(size.width, size.height)
                                )
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
                layout = HorizontalChildPanelsLayout(
                    dualWeights = Pair(first = 0.45F, second = 0.55F),
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
        }
    }
}