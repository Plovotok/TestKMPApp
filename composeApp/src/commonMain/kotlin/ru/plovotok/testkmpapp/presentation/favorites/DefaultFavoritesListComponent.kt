package ru.plovotok.testkmpapp.presentation.favorites

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.testkmpapp.presentation.ViewModelFactoryProvider
import ru.plovotok.testkmpapp.presentation.base.getViewModel

class DefaultFavoritesListComponent(
    private val componentContext: ComponentContext,
    private val onInfo: (BookPreview) -> Unit,
    private val onBack: () -> Unit
): FavoritesListComponent, ComponentContext by componentContext, ViewModelFactoryProvider {

    private val vm = getViewModel { vmFactory.createFavoritesViewModel() }

    override val state: Value<FavoritesListComponent.UiState> = vm.state
    override val query: Value<String> = vm.query
    override fun onQueryChanged(newQuery: String) = vm.onQueryChanged(newQuery)

    override fun removeFromFavorites(book: BookPreview) = vm.removeFromFavorites(book)
    override fun onBookClicked(book: BookPreview) = onInfo(book)
    override fun onBackClicked() = onBack()
}