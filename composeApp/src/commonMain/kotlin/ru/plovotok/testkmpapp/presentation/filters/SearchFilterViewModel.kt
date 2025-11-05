package ru.plovotok.testkmpapp.presentation.filters

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plovotok.shared.domain.BooksRepository
import ru.plovotok.shared.domain.models.Genre
import ru.plovotok.testkmpapp.presentation.base.BaseViewModel

class SearchFilterViewModel(
    currentGenres: List<Genre>,
    private val repository: BooksRepository
): BaseViewModel(), KoinComponent {

    private val allGenres = repository.getGenres()

    val query: MutableValue<String> = MutableValue("")

    val state: MutableValue<SearchFiltersComponent.UiState> = MutableValue(
        SearchFiltersComponent.UiState(query.value, allGenres, currentGenres))

    private var searchJob: Job? = null

    fun onQueryChanged(newQuery: String) {
        searchJob?.cancel()
        query.update { newQuery }
        searchJob = viewModelScope.launch(Dispatchers.Default) {
            delay(300)
            val newGenres = if (newQuery.isBlank()) {
                allGenres
            } else {
                allGenres.filter {
                    it.displayName.contains(newQuery, ignoreCase = true)
                }
            }
            state.update { it.copy(query = newQuery, items = newGenres) }
        }
    }

    fun onGenreSelectChange(genre: Genre) {
        state.update {
            val genres = it.selectedItems
            if (genres.contains(genre)) {
                it.copy(selectedItems = genres - genre)
            } else {
                it.copy(selectedItems = genres + genre)
            }
        }
    }

}