package ru.plovotok.testkmpapp.presentation.home

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import ru.plovotok.testkmpapp.domain.models.BookPreview
import ru.plovotok.testkmpapp.domain.models.Genre
import ru.plovotok.testkmpapp.presentation.filters.SearchFiltersComponent

interface BookListComponent {

    val filterDialog: Value<ChildSlot<*, SearchFiltersComponent>>

    val state: Value<BooksState>

    val currentGenres: Value<List<Genre>>

    fun showBookInfo(book: BookPreview)
    fun openFavorites()

    fun getBooks(query: String)

    fun showFiltersDialog()

    fun loadNext()

    fun retry()

    val query: Value<String>

    fun onQueryChanged(newQuery: String)
    fun removeBookFromFavorites(book: BookPreview)

    @Stable
    data class BooksState(
        val query: String = "",
        val books: List<BookPreview> = emptyList(),
        val isRefreshing: Boolean = true,
        val refreshError: Throwable? = null,
        val isAppending: Boolean = false,
        val appendError: Throwable? = null,
        val favorites: List<Int> = emptyList(),
        val filters: List<Genre> = emptyList()
    )
}