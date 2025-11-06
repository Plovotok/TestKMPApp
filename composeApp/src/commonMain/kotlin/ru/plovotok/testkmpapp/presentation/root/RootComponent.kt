package ru.plovotok.testkmpapp.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.plovotok.testkmpapp.presentation.favorites.FavoritesComponent
import ru.plovotok.testkmpapp.presentation.home.HomeComponent

interface RootComponent: BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>

    fun onBack()

    sealed class Child() {
        class Home(val component: HomeComponent): Child()
        class Favorites(val component: FavoritesComponent): Child()
    }
}