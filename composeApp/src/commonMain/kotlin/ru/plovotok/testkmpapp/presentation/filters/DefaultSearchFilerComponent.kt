package ru.plovotok.testkmpapp.presentation.filters

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.plovotok.shared.domain.models.Genre
import ru.plovotok.testkmpapp.presentation.base.getViewModel

class DefaultSearchFilerComponent(
    private val ctx: ComponentContext,
    private val currentGenres: List<Genre>,
    private val onNewGenres: (genres: List<Genre>) -> Unit,
    private val onDismiss: () -> Unit
) : SearchFiltersComponent, ComponentContext by ctx {

    private val vm = getViewModel { SearchFilterViewModel(currentGenres) }

    override val query: MutableValue<String> = vm.query

    override val state: Value<SearchFiltersComponent.UiState> = vm.state

    override fun setNewGenres(genres: List<Genre>) = onNewGenres(genres)

    override fun onQueryChanged(newQuery: String) = vm.onQueryChanged(newQuery)

    override fun onGenreSelectChange(genre: Genre) = vm.onGenreSelectChange(genre)

    override fun dismiss() = onDismiss()

}