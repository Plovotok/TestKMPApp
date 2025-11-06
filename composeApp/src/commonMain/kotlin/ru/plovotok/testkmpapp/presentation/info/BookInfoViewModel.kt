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

    val state: MutableValue<SimilarBookInfoComponent.BookState> = MutableValue(SimilarBookInfoComponent.BookState())

    init {
        getBookInfo()
        getSimilar()
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

    private fun getSimilar() {
        suspend {
            repository.getSimilarBooks(preview.id)
        }.runInViewModelScope(
            onSuccess = { list ->
                state.update { it.copy(similar = list) }
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun addBookToFavorites() {
        val book = BookPreview(
            id = preview.id,
            title = preview.title ?: state.value.fullInfo?.title,
            subTitle = preview.subTitle ?: state.value.fullInfo?.subTitle,
            image = preview.image ?: state.value.fullInfo?.image
        )
        suspend { repository.addBookToFavorite(book) }.runInViewModelScope()
    }

    fun removeBookFromFavorites() {
        suspend { repository.removeBookFromFavorite(preview) }.runInViewModelScope()
    }

}