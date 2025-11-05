package ru.plovotok.testkmpapp.presentation.favorites

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import ru.plovotok.testkmpapp.presentation.getPredictiveBackAnimatable
import ru.plovotok.testkmpapp.presentation.info.BookInfoContent
import ru.plovotok.testkmpapp.presentation.ui.BaseScreen
import ru.plovotok.testkmpapp.presentation.ui.components.DynamicWeightChildPanels
import ru.plovotok.testkmpapp.presentation.ui.components.screens.EmptyScreen

private val StartWeight = 0.45f

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun FavoritesContent(
    component: FavoritesComponent,
    modifier: Modifier = Modifier
) {
    val panels by component.panels.subscribeAsState()

    val activeBookId = panels.details?.instance?.preview?.id

    var weight: Float by rememberSaveable {
        mutableFloatStateOf(StartWeight)
    }

    BaseScreen(
        contentWindowInsets = WindowInsets(0.dp)
    ) {
        val mode = panels.mode
        DynamicWeightChildPanels(
            currentLeftWeight = { weight },
            onWeightChange = {
                weight = it
            },
            panels = panels,
            mainChild = {
                FavoritesListContent(
                    component = it.instance,
                    activeBookId = activeBookId,
                    modifier = Modifier
                        .fillMaxSize()
                )
            },
            detailsChild = {
                BookInfoContent(
                    component = it.instance,
                    showBackButton = mode == ChildPanelsMode.SINGLE,
                    modifier = Modifier.fillMaxSize()
                )
            },
            secondPanelPlaceholder = {
                EmptyScreen(title = "Choose book from list")
            },
            component = component,
            predictiveBackParams = {
                PredictiveBackParams(
                    backHandler = component.backHandler,
                    onBack = component::onBack,
                    animatable = ::getPredictiveBackAnimatable,
                )
            },
            modifier = modifier.fillMaxSize()
        )
    }
}