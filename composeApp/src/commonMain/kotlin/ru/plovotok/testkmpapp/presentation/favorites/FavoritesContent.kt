package ru.plovotok.testkmpapp.presentation.favorites

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import ru.plovotok.testkmpapp.presentation.DeeplinkHandler
import ru.plovotok.testkmpapp.presentation.DeeplinkHelper
import ru.plovotok.testkmpapp.presentation.info.BookInfoWrapperContent
import ru.plovotok.testkmpapp.presentation.ui.BaseScreen
import ru.plovotok.testkmpapp.presentation.ui.components.DynamicWeightChildPanels
import ru.plovotok.testkmpapp.presentation.ui.components.screens.EmptyScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun FavoritesContent(
    component: FavoritesComponent,
    modifier: Modifier = Modifier
) {
    val panels by component.panels.subscribeAsState()

    val activeBookId = panels.details?.instance?.preview?.id

    val weight by component.weight.subscribeAsState()

    BaseScreen(
        contentWindowInsets = WindowInsets(0.dp)
    ) {
        val mode = panels.mode

        DeeplinkHandler {
            when (it) {
                is DeeplinkHelper.DeeplinkAction.BookDetails -> {
                    component.onBookInfo(it.id)
                }
            }
        }

        DynamicWeightChildPanels(
            currentLeftWeight = { weight },
            onWeightChange = component::onWeightChange,
            onDragReleased = component::saveNewWeight,
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
                BookInfoWrapperContent(
                    component = it.instance,
                    showBackButton = mode == ChildPanelsMode.SINGLE,
                    modifier = Modifier.fillMaxSize()
                )
            },
            secondPanelPlaceholder = {
                EmptyScreen(title = "Choose book from list")
            },
            component = component,
            predictiveBackParams = { null },
            modifier = modifier.fillMaxSize()
        )
    }
}