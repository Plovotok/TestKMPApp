package ru.plovotok.testkmpapp.presentation.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.testkmpapp.presentation.ViewModelFactoryProvider
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DefaultBookInfoComponentWrapper(
    private val componentContext: ComponentContext,
    override val preview: BookPreview,
    private val goBack: () -> Unit
) : BookInfoComponentWrapper, ComponentContext by componentContext {

    private val navigation = StackNavigation<ChildBook>()
    @OptIn(ExperimentalUuidApi::class)
    override val booksStack: Value<ChildStack<ChildBook, BookInfoComponent>> = childStack(
        source = navigation,
        serializer = ChildBook.serializer(),
        initialConfiguration = ChildBook(preview, false, 0),
        handleBackButton = true,
        childFactory = ::createChildBookComponent
    )

    @OptIn(DelicateDecomposeApi::class, ExperimentalUuidApi::class)
    private fun createChildBookComponent(book: ChildBook, ctx: ComponentContext) =
        DefaultBookInfoComponent(
            componentContext = ctx,
            preview = book.preview,
            onGoBack = {
                if (booksStack.value.items.size > 1) {
                    navigation.pop()
                } else {
                    goBack()
                }
            },
            onSimilar = {
                navigation.push(ChildBook(it, true, book.modelId + 1))
            }
        )

    override fun onBack() {
        navigation.pop()
    }

    @Serializable
    data class ChildBook(
        val preview: BookPreview,
        val hasParent: Boolean,
        val modelId: Int
    )
}