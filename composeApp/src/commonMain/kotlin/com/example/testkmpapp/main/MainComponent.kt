package com.example.testkmpapp.main

import com.arkivanov.decompose.value.Value

interface MainComponent {

    fun onShowWelcomeClicked(promo: String)

    val uiState: Value<UiState>

    data class UiState(
        val items: List<String> = emptyList(),
        val isLoading: Boolean = true,
        val error: Exception? = null
    )
}