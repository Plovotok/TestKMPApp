package ru.plovotok.testkmpapp.presentation.filters

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.value.Value
import ru.plovotok.shared.domain.models.Genre

interface SearchFiltersComponent {

    val query: Value<String>

    val state: Value<UiState>

    fun setNewGenres(genres: List<Genre>)

    fun onQueryChanged(newQuery: String)

    fun onGenreSelectChange(genre: Genre)

    fun dismiss()

    @Stable
    data class UiState(
        val query: String = "",
        val items: List<Genre> = emptyList(),
        val selectedItems: List<Genre> = emptyList()
    )
}