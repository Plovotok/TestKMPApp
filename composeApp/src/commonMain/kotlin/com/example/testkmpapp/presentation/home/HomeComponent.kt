package com.example.testkmpapp.presentation.home

import com.arkivanov.decompose.value.Value
import com.example.testkmpapp.data.models.BookPreview

interface HomeComponent {

    val state: Value<BooksState>

    fun showBookInfo(id: Int)

    fun getBooks(query: String)

    fun loadNext()

    fun retry()

    data class BooksState(
        val books: List<BookPreview> = emptyList(),
        val isRefreshing: Boolean = true,
        val refreshError: Throwable? = null,
        val isAppending: Boolean = false,
        val appendError: Throwable? = null,
    )
}