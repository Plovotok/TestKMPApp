package com.example.testkmpapp.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.example.testkmpapp.domain.models.BookPreview
import com.example.testkmpapp.presentation.base.getViewModel

class DefaultHomeComponent(
    private val componentContext: ComponentContext,
    private val onBookClicked: (book: BookPreview) -> Unit
) : HomeComponent, ComponentContext by componentContext {

    private val vm: MainViewModel = getViewModel { MainViewModel() }

    override val state: Value<HomeComponent.BooksState> = vm.state

    override fun loadNext() = vm.loadNextItems()

    override fun showBookInfo(book: BookPreview) = onBookClicked(book)

    override fun retry() = vm.retry()

    override fun getBooks(query: String) = vm.searchBooks(query)

}