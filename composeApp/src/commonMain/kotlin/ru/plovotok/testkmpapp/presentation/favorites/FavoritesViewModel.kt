package ru.plovotok.testkmpapp.presentation.favorites

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plovotok.shared.domain.BooksRepository
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.testkmpapp.presentation.base.BaseViewModel

class FavoritesViewModel(
    private val repository: BooksRepository
): BaseViewModel(), KoinComponent {

    val query: MutableValue<String> = MutableValue("")

    val state: MutableValue<FavoritesListComponent.UiState> = MutableValue(FavoritesListComponent.UiState(query = query.value))

    private var allItems: MutableValue<List<BookPreview>> = MutableValue(emptyList())

    init {
        repository.getFavorites().collectInViewModel { items ->
            allItems.update { items }
            state.update {
                it.copy(
                    totalItems = items.size,
                    filtered = filterItems(query.value, allItems.value),
                    isLoading = false
                )
            }
        }
    }

    private fun filterItems(query: String, items: List<BookPreview>): List<BookPreview> {
        return if (query.isBlank()) items else items.filter {
            it.title.contains(query, true)
        }
    }

    private var searchJob: Job? = null

    fun onQueryChanged(newQuery: String) {
        searchJob?.cancel()
        query.update { newQuery }
        searchJob = viewModelScope.launch(Dispatchers.Default) {
            delay(300)
            state.update {
                it.copy(
                    query = newQuery,
                    filtered = filterItems(query.value, allItems.value)
                )
            }
        }
    }


    fun removeFromFavorites(book: BookPreview) {
        suspend { repository.removeBookFromFavorite(book) }.runInViewModelScope()
    }
}