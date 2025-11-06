package ru.plovotok.testkmpapp.presentation.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.testkmpapp.presentation.ViewModelFactoryProvider
import ru.plovotok.testkmpapp.presentation.base.getViewModel

class DefaultBookInfoComponent(
    private val componentContext: ComponentContext,
    override val preview: BookPreview,
    private val onGoBack: () -> Unit,
    private val onSimilar: (preview: BookPreview) -> Unit
): BookInfoComponent, ComponentContext by componentContext, ViewModelFactoryProvider {

    private val vm = getViewModel { vmFactory.createBookInfoViewModel(book = preview) }

    override val state: Value<BookInfoComponent.BookState> = vm.state

    override fun onSimilarClick(preview: BookPreview) = onSimilar(preview)

    override fun addBookToFavorites() = vm.addBookToFavorites()

    override fun removeBookFromFavorites() = vm.removeBookFromFavorites()

    override fun getBookInfo() = vm.getBookInfo()

    override fun onBack() = onGoBack()
}