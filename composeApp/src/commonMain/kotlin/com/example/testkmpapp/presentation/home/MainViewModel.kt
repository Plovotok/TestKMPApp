package com.example.testkmpapp.presentation.home

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.testkmpapp.domain.models.BookPagingResponse
import com.example.testkmpapp.domain.BooksRepository
import com.example.testkmpapp.presentation.Paginator
import com.example.testkmpapp.presentation.base.BaseViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel: BaseViewModel(), KoinComponent {

    private val repository: BooksRepository by inject()

    val state: MutableValue<HomeComponent.BooksState> = MutableValue(HomeComponent.BooksState())

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

    fun searchBooks(query: String) {
        paginator = getPaginator(query)
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
}