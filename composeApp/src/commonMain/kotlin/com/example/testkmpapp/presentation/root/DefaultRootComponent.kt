package com.example.testkmpapp.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.testkmpapp.domain.models.BookPreview
import com.example.testkmpapp.presentation.favorites.DefaultFavoritesComponent
import com.example.testkmpapp.presentation.favorites.FavoritesComponent
import com.example.testkmpapp.presentation.home.DefaultHomeComponent
import com.example.testkmpapp.presentation.home.HomeComponent
import com.example.testkmpapp.presentation.info.DefaultBookInfoComponent
import com.example.testkmpapp.presentation.info.BookInfoComponent
import com.example.testkmpapp.presentation.root.RootComponent.Child.*
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    private val ctx: ComponentContext
) : RootComponent, ComponentContext by ctx {

    private val navigation = StackNavigation<Config>()


    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(config: Config, childComponentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Home -> Home(homeComponent(childComponentContext))
            is Config.BookInfo -> BookInfo(bookInfoComponent(config.preview, childComponentContext))
            is Config.Favorites -> Favorites(favoritesComponent(childComponentContext))
        }

    private fun homeComponent(componentContext: ComponentContext): HomeComponent =
        DefaultHomeComponent(
            componentContext = componentContext,
            onBookClicked = {
                navigation.pushNew(Config.BookInfo(it))
            },
            onFavorites = {
                navigation.pushNew(Config.Favorites)
            }
        )

    private fun bookInfoComponent(preview: BookPreview, componentContext: ComponentContext): BookInfoComponent =
        DefaultBookInfoComponent(
            preview = preview,
            componentContext = componentContext,
            onGoBack = ::onBack
        )

    private fun favoritesComponent(componentContext: ComponentContext): FavoritesComponent =
        DefaultFavoritesComponent(
            componentContext = componentContext,
            onInfo = {
                navigation.pushNew(Config.BookInfo(it))
            },
            onBack = ::onBack
        )

    override fun onBack() {
        navigation.pop()
    }

    @Serializable
    sealed interface Config {

        @Serializable
        data object Home: Config

        @Serializable
        data class BookInfo(val preview: BookPreview): Config

        @Serializable
        data object Favorites: Config
    }

}