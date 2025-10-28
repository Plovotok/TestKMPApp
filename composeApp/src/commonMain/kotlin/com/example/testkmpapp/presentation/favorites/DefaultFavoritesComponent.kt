package com.example.testkmpapp.presentation.favorites

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.example.testkmpapp.domain.models.BookPreview
import com.example.testkmpapp.presentation.base.getViewModel

class DefaultFavoritesComponent(
    private val componentContext: ComponentContext,
    private val onInfo: (BookPreview) -> Unit,
    private val onBack: () -> Unit
): FavoritesComponent, ComponentContext by componentContext {

    private val vm = getViewModel { FavoritesViewModel() }

    override val state: Value<FavoritesComponent.UiState> = vm.state
    override val query: Value<String> = vm.query
    override fun onQueryChanged(newQuery: String) = vm.onQueryChanged(newQuery)

    override fun removeFromFavorites(book: BookPreview) = vm.removeFromFavorites(book)
    override fun onBookClicked(book: BookPreview) = onInfo(book)
    override fun onBackClicked() = onBack()
}