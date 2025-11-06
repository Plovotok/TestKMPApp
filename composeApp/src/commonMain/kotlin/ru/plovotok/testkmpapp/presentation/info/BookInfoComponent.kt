package ru.plovotok.testkmpapp.presentation.info

import com.arkivanov.decompose.value.Value
import ru.plovotok.shared.domain.models.BookPreview

interface BookInfoComponent {

    val state: Value<BookState>

    data class BookState(
        val fullInfo: BookPreview? = null,
        val isLoading: Boolean = true,
        val error: Throwable? = null,
        val isFavorite: Boolean = false,
        val similar: List<BookPreview>? = null
    )

    val preview: BookPreview

    fun onSimilarClick(preview: BookPreview)

    fun addBookToFavorites()
    fun removeBookFromFavorites()

    fun getBookInfo()

    fun onBack()
}