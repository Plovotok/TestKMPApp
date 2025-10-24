package com.example.testkmpapp.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate

class DefaultHomeComponent(
    private val componentContext: ComponentContext,
    private val onBookClicked: (id: Int) -> Unit
) : HomeComponent, ComponentContext by componentContext {

    private val vm: MainViewModel = instanceKeeper.getOrCreate {
        MainViewModel()
    }

    override val state: Value<HomeComponent.BooksState> = vm.state

    override fun loadNext() = vm.loadNextItems()

    override fun showBookInfo(id: Int) = onBookClicked(id)

    override fun retry() = vm.retry()

    override fun getBooks(query: String) = vm.searchBooks(query)

}