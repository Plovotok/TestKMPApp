package ru.plovotok.testkmpapp.presentation

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.shared.domain.models.Genre
import ru.plovotok.testkmpapp.presentation.favorites.FavoritesViewModel
import ru.plovotok.testkmpapp.presentation.filters.SearchFilterViewModel
import ru.plovotok.testkmpapp.presentation.home.SearchViewModel
import ru.plovotok.testkmpapp.presentation.info.BookInfoViewModel
import ru.plovotok.testkmpapp.presentation.root.RootViewModel

class ViewModelFactory: KoinComponent {

    private val provider = getKoin()

    fun createRootViewModel() : RootViewModel {
        return RootViewModel(provider.get())
    }

    fun createSearchViewModel(): SearchViewModel {
        return SearchViewModel(provider.get(), provider.get())
    }

    fun createSearchFilterViewModel(currentGenres: List<Genre>): SearchFilterViewModel {
        return SearchFilterViewModel(currentGenres, provider.get())
    }

    fun createBookInfoViewModel(book: BookPreview): BookInfoViewModel {
        return BookInfoViewModel(book, provider.get())
    }

    fun createFavoritesViewModel(): FavoritesViewModel {
        return FavoritesViewModel(provider.get())
    }
}

interface ViewModelFactoryProvider: KoinComponent {
    val vmFactory: ViewModelFactory
        get() = inject<ViewModelFactory>().value
}
