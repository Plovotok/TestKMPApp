package ru.plovotok.testkmpapp.presentation.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import org.koin.core.component.KoinComponent
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.testkmpapp.presentation.base.getViewModel

class DefaultBookInfoComponent(
    private val componentContext: ComponentContext,
    override val preview: BookPreview,
    private val onGoBack: () -> Unit
) : BookInfoComponent, ComponentContext by componentContext, KoinComponent {

    private val vm = getViewModel { BookInfoViewModel(preview) }

    override val state: Value<BookInfoComponent.BookState> = vm.state
    override fun addBookToFavorites() = vm.addBookToFavorites()
    override fun removeBookFromFavorites() = vm.removeBookFromFavorites()

    override fun getBookInfo() = vm.getBookInfo()
    override fun onBack() = onGoBack()
}