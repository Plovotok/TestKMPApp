package com.example.testkmpapp.presentation.favorites

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.value.Value
import com.example.testkmpapp.domain.models.BookPreview

interface FavoritesListComponent {

    fun removeFromFavorites(book: BookPreview)
    fun onBookClicked(book: BookPreview)
    fun onBackClicked()

    val state: Value<UiState>

    val query: Value<String>
    fun onQueryChanged(newQuery: String)

    @Stable
    data class UiState(
        val query: String = "",
        val totalItems: Int = 0,
        val filtered: List<BookPreview> = emptyList(),
        val isLoading: Boolean = true
    )
}