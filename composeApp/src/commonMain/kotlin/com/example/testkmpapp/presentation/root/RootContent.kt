package com.example.testkmpapp.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.example.testkmpapp.presentation.backAnimation
import com.example.testkmpapp.presentation.favorites.FavoritesContent
import com.example.testkmpapp.presentation.home.HomeContent
import com.example.testkmpapp.presentation.info.BookInfoContent
import com.example.testkmpapp.presentation.ui.theme.BooksTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    BooksTheme(
        modifier = modifier.fillMaxSize()
    ) {
        Children(
            stack = component.stack,
            modifier = Modifier.fillMaxSize(),
            animation = backAnimation(component.backHandler, component::onBack)
        ) {
            when (val instance = it.instance) {
                is RootComponent.Child.Home -> HomeContent(component = instance.component)
                is RootComponent.Child.BookInfo -> BookInfoContent(component = instance.component)
                is RootComponent.Child.Favorites -> FavoritesContent(component = instance.component)
            }
        }
    }
}