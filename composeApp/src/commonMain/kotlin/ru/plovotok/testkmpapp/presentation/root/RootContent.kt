package ru.plovotok.testkmpapp.presentation.root

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.plovotok.testkmpapp.presentation.backAnimation
import ru.plovotok.testkmpapp.presentation.favorites.FavoritesContent
import ru.plovotok.testkmpapp.presentation.favorites.FavoritesListComponent
import ru.plovotok.testkmpapp.presentation.home.HomeContent
import ru.plovotok.testkmpapp.presentation.info.BookInfoContent
import ru.plovotok.testkmpapp.presentation.ui.theme.BooksTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(
    component: RootComponent,
    isDark: Boolean = isSystemInDarkTheme(),
    modifier: Modifier = Modifier
) {
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
    }
}