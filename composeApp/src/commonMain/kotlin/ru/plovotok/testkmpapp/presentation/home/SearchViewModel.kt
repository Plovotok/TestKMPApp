package ru.plovotok.testkmpapp.presentation.home

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import ru.plovotok.testkmpapp.domain.BooksRepository
import ru.plovotok.testkmpapp.domain.models.BookPagingResponse
import ru.plovotok.testkmpapp.domain.models.BookPreview
import ru.plovotok.testkmpapp.domain.models.Genre
import ru.plovotok.testkmpapp.presentation.Paginator
import ru.plovotok.testkmpapp.presentation.base.BaseViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel: BaseViewModel(), KoinComponent {

    private val repository: BooksRepository by inject()

    val state: MutableValue<BookListComponent.BooksState> = MutableValue(BookListComponent.BooksState())

    val currentGenres: MutableValue<List<Genre>> = MutableValue(emptyList())

    init {
        repository.getFavorites().collectInViewModel { list ->
            state.update {
                it.copy(favorites = list.map { it.id })
            }
        }
    }

    private val pageSize = 30

    private fun getPaginator(
        query: String = "",
        genres: List<String> = emptyList(),
        authors: List<String> = emptyList()
    ) = Paginator(
        initialKey = 0,
        onLoadUpdated = { page, isUpdating ->
            state.update {
                if (page == 0 && isUpdating) {
                    it.copy(isRefreshing = true)
                } else {
                    it.copy(isRefreshing = false, isAppending = isUpdating)
                }
            }
        },
        onRequest = { page ->
            try {
                val result = repository.getBooks(
                    query = query,
                    genres = genres,
                    authors = authors,
                    number = pageSize,
                    offset = pageSize * page
                )
                Result.success(result)
            } catch (e: Exception) {
                e.printStackTrace()
                currentCoroutineContext().ensureActive()
                Result.failure(e)
            }
        },
        getNextKey = { currentKey, data ->
            currentKey + 1
        },
        onError = { currentKey, error ->
            state.update {
                if (currentKey == 0) {
                    it.copy(refreshError = error)
                } else {
                    it.copy(appendError = error)
                }
            }
        },
        onSuccess = { data, key ->
            val items = data.books.flatMap { it }
            state.update {
                it.copy(books = it.books + items)
            }
        },
        endReached = { page, data ->
            page * pageSize >= data.total || data.books.flatMap { it }.isEmpty()
        }
    )

    private var paginator: Paginator<Int, BookPagingResponse> = getPaginator()

    init {
        loadNextItems()
    }

    fun searchBooks(query: String, genres: List<Genre>) {
        currentGenres.update { genres }
        state.update {
            it.copy(query = query, books = emptyList(), isRefreshing = true, refreshError = null, isAppending = false, appendError = null)
        }
        paginator = getPaginator(
            query,
            genres = genres.map { it.requestName }
        )
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun retry() {
        viewModelScope.launch {
            paginator.retry()
        }
    }

    fun removeBookFromFavorites(book: BookPreview) {
        viewModelScope.launch {
            try {
                repository.removeBookFromFavorite(book)
            } catch (e: Throwable) {
                ensureActive()
                e.printStackTrace()
            }
        }
    }
}