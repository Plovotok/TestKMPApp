package com.example.testkmpapp.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.example.testkmpapp.presentation.favorites.FavoritesComponent
import com.example.testkmpapp.presentation.filters.SearchFiltersComponent
import com.example.testkmpapp.presentation.home.HomeComponent
import com.example.testkmpapp.presentation.info.BookInfoComponent

interface RootComponent: BackHandlerOwner {

    val stack: Value<ChildStack<*, Child>>

    fun onBack()

    sealed class Child() {
        class Home(val component: HomeComponent): Child()
        class Favorites(val component: FavoritesComponent): Child()
    }
}