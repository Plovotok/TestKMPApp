package com.example.testkmpapp.presentation.info

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.testkmpapp.domain.BooksRepository
import com.example.testkmpapp.domain.models.BookPreview
import com.example.testkmpapp.presentation.base.BaseViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookInfoViewModel(
    private val preview: BookPreview
): BaseViewModel(), KoinComponent {

    private val repository: BooksRepository by inject()

    val state: MutableValue<BookInfoComponent.BookState> = MutableValue(BookInfoComponent.BookState())

    init {
        getBookInfo()
        repository.getFavorites().collectInViewModel {
            it.firstOrNull { it.id == preview.id }?.let {
                state.update { it.copy(isFavorite = true) }
            } ?: state.update { it.copy(isFavorite = false) }
        }
    }

    fun getBookInfo() {
        suspend {
            state.update { it.copy(isLoading = true) }
            repository.getBookInfo(preview.id)
        }.runInViewModelScope(
            onSuccess = { result ->
                state.update { it.copy(fullInfo = result, isLoading = false, error = null) }
            },
            onError = { error ->
                state.update { it.copy(isLoading = false, error = error, fullInfo = null) }
            }
        )
    }

    fun addBookToFavorites() {
        suspend { repository.addBookToFavorite(preview) }.runInViewModelScope()
    }

    fun removeBookFromFavorites() {
        suspend { repository.removeBookFromFavorite(preview) }.runInViewModelScope()
    }

}