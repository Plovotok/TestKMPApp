package ru.plovotok.testkmpapp.presentation.info

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plovotok.shared.domain.BooksRepository
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.testkmpapp.presentation.base.BaseViewModel

class BookInfoViewModel(
    private val preview: BookPreview,
    private val repository: BooksRepository
): BaseViewModel(), KoinComponent {

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