package ru.plovotok.testkmpapp.presentation.root

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.plovotok.testkmpapp.presentation.DeeplinkHandler
import ru.plovotok.testkmpapp.presentation.DeeplinkHelper
import ru.plovotok.testkmpapp.presentation.backAnimation
import ru.plovotok.testkmpapp.presentation.favorites.FavoritesContent
import ru.plovotok.testkmpapp.presentation.favorites.FavoritesListComponent
import ru.plovotok.testkmpapp.presentation.home.HomeContent
import ru.plovotok.testkmpapp.presentation.info.BookInfoContent
import ru.plovotok.testkmpapp.presentation.ui.colorScheme
import ru.plovotok.testkmpapp.presentation.ui.components.bottom_sheet.AdaptiveDialogLayout
import ru.plovotok.testkmpapp.presentation.ui.components.bottom_sheet.rememberAdaptiveDialogState
import ru.plovotok.testkmpapp.presentation.ui.theme.BooksTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(
    component: RootComponent,
    isDark: Boolean = isSystemInDarkTheme(),
    modifier: Modifier = Modifier
) {
    val dialog by component.dialog.subscribeAsState()

    DeeplinkHandler {
        when (it) {
            is DeeplinkHelper.DeeplinkAction.BookDetails -> {
                component.onBookInfo(it.id)
            }
        }
    }

    BooksTheme(
        isDark = isDark,
        modifier = modifier.fillMaxSize()
    ) {
        Children(
            stack = component.stack,
            modifier = Modifier.fillMaxSize(),
            animation = backAnimation(component.backHandler, component::onBack)
        ) {
            when (val instance = it.instance) {
                is RootComponent.Child.Home -> HomeContent(component = instance.component)
                is RootComponent.Child.Favorites -> FavoritesContent(component = instance.component)
            }
        }
        dialog.child?.let { child ->
            val scope= rememberCoroutineScope()

            val state = rememberAdaptiveDialogState()

            suspend fun dismiss() {
                state.dismiss()
                component.closeDialog()
            }

            AdaptiveDialogLayout(
                state = state,
                onDismiss = {
                    scope.launch {
                        dismiss()
                    }
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BookInfoContent(
                        component = child.instance,
                        showBackButton = false,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}