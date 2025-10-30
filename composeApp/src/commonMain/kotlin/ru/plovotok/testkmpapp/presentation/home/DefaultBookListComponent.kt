package ru.plovotok.testkmpapp.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.slot.navigate
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import ru.plovotok.testkmpapp.domain.models.BookPreview
import ru.plovotok.testkmpapp.domain.models.Genre
import ru.plovotok.testkmpapp.presentation.base.getViewModel
import ru.plovotok.testkmpapp.presentation.filters.DefaultSearchFilerComponent
import ru.plovotok.testkmpapp.presentation.filters.SearchFiltersComponent
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

class DefaultBookListComponent(
    private val componentContext: ComponentContext,
    private val onBookClicked: (book: BookPreview) -> Unit,
    private val onCloseDetails: () -> Unit,
    private val onFavorites: () -> Unit
): BookListComponent, ComponentContext by componentContext {

    private val vm: SearchViewModel = getViewModel { SearchViewModel() }

    private val savedQuery = stateKeeper.consume("query", String.serializer()) ?: ""

    override val query: MutableValue<String> = MutableValue(savedQuery)

    init {
        stateKeeper.register("query", String.serializer()) { query.value }
    }

    override fun onQueryChanged(newQuery: String) {
        query.update { newQuery }
    }

    override val currentGenres: Value<List<Genre>> = vm.currentGenres

    private val dialogNavigation = SlotNavigation<DialogConfig>()

    override val filterDialog: Value<ChildSlot<*, SearchFiltersComponent>> =
        childSlot(
            source = dialogNavigation,
            serializer = DialogConfig.serializer(),
            handleBackButton = true
        ) { config, context ->
            DefaultSearchFilerComponent(
                ctx = context,
                currentGenres = config.activeGenres,
                onNewGenres = {
                    vm.searchBooks("", it)
                    onCloseDetails()
                },
                onDismiss = {
                    dialogNavigation.dismiss()
                }
            )
        }

    override fun showFiltersDialog() {
        dialogNavigation.navigate { DialogConfig(currentGenres.value) }
    }

    override val state: Value<BookListComponent.BooksState> = vm.state

    override fun loadNext() = vm.loadNextItems()

    override fun showBookInfo(book: BookPreview) = onBookClicked(book)
    override fun openFavorites() = onFavorites()

    override fun retry() = vm.retry()

    override fun removeBookFromFavorites(book: BookPreview) = vm.removeBookFromFavorites(book)

    override fun getBooks(query: String) = vm.searchBooks(query, currentGenres.value)

    @Serializable
    private data class DialogConfig(
        val activeGenres: List<Genre> = emptyList()
    )
}