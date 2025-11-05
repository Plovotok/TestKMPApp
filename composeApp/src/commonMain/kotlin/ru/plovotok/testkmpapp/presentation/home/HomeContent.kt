package ru.plovotok.testkmpapp.presentation.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun HomeContent(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {

    val panels by component.panels.subscribeAsState()

    val activeBookId = panels.details?.instance?.preview?.id

    val weight: Float by component.weight.subscribeAsState()

    BaseScreen(
        contentWindowInsets = WindowInsets(0.dp)
    ) {
        val panels by component.panels.subscribeAsState()

        val mode = panels.mode

        DynamicWeightChildPanels(
            currentLeftWeight = { weight },
            onWeightChange = component::onWeightChange,
            panels = panels,
            mainChild = {
                BookListContent(
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
            predictiveBackParams = {
                PredictiveBackParams(
                    backHandler = component.backHandler,
                    onBack = component::onBack,
                    animatable = ::getPredictiveBackAnimatable,
                )
            },
            modifier = modifier.fillMaxSize(),
            component = component,
        )
    }
}