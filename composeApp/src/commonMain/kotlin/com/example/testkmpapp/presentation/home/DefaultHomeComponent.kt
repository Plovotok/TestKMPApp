package com.example.testkmpapp.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.slot.navigate
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.testkmpapp.domain.models.BookPreview
import com.example.testkmpapp.domain.models.Genre
import com.example.testkmpapp.presentation.base.getViewModel
import com.example.testkmpapp.presentation.filters.DefaultSearchFilerComponent
import com.example.testkmpapp.presentation.filters.SearchFiltersComponent
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

class DefaultHomeComponent(
    private val componentContext: ComponentContext,
    private val onBookClicked: (book: BookPreview) -> Unit
) : HomeComponent, ComponentContext by componentContext {

    private val vm: SearchViewModel = getViewModel { SearchViewModel() }

    private val dialogNavigation = SlotNavigation<DialogConfig>()

    override val filterDialog: Value<ChildSlot<*, SearchFiltersComponent>> =
        childSlot(
            source = dialogNavigation,
            serializer = DialogConfig.serializer(),
            handleBackButton = true
        ) { config, context ->
            DefaultSearchFilerComponent(
                ctx = context,
                currentGenres = vm.currentGenres,
                onNewGenres = {
                    vm.applyGenres(it)
                },
                onDismiss = {
                    dialogNavigation.dismiss()
                }
            )
        }

    override fun showFiltersDialog() {
        dialogNavigation.navigate { DialogConfig() }
    }

    override val state: Value<HomeComponent.BooksState> = vm.state

    override fun loadNext() = vm.loadNextItems()

    override fun showBookInfo(book: BookPreview) = onBookClicked(book)

    override fun retry() = vm.retry()

    private val savedQuery = stateKeeper.consume("query", String.serializer()) ?: ""

    override val query: MutableValue<String> = MutableValue(savedQuery)

    init {
        stateKeeper.register("query", String.serializer()) { query.value }
    }

    override fun onQueryChanged(newQuery: String) {
        query.update { newQuery }
    }

    override fun getBooks(query: String) = vm.searchBooks(query)

    @Serializable
    private data class DialogConfig(
        val activeGenres: List<Genre> = emptyList()
    )

}