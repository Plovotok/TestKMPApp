package ru.plovotok.testkmpapp.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.plovotok.testkmpapp.presentation.favorites.DefaultFavoritesComponent
import ru.plovotok.testkmpapp.presentation.favorites.FavoritesComponent
import ru.plovotok.testkmpapp.presentation.home.DefaultHomeComponent
import ru.plovotok.testkmpapp.presentation.home.HomeComponent
import ru.plovotok.testkmpapp.presentation.root.RootComponent.Child.Favorites
import ru.plovotok.testkmpapp.presentation.root.RootComponent.Child.Home

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
            is Config.Favorites -> Favorites(favoritesComponent(childComponentContext))
        }

    private fun homeComponent(componentContext: ComponentContext): HomeComponent =
        DefaultHomeComponent(
            componentContext = componentContext,
            onFavorites = {
                navigation.pushNew(Config.Favorites)
            }
        )

    private fun favoritesComponent(componentContext: ComponentContext): FavoritesComponent =
        DefaultFavoritesComponent(
            componentContext = componentContext,
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
        data object Favorites: Config
    }

}