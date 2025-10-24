package com.example.testkmpapp.presentation.info

import com.arkivanov.decompose.value.Value


interface BookInfoComponent {

    val state: Value<BookState>

    data class BookState(
        val info: Int = 0,
        val isLoading: Boolean = true,
        val error: Exception? = null
    )

    fun onBack()
}