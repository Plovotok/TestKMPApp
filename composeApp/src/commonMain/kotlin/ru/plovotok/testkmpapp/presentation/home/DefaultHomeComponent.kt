package ru.plovotok.testkmpapp.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.arkivanov.decompose.router.panels.Panels
import com.arkivanov.decompose.router.panels.PanelsNavigation
import com.arkivanov.decompose.router.panels.childPanels
import com.arkivanov.decompose.router.panels.navigate
import com.arkivanov.decompose.router.panels.pop
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.testkmpapp.presentation.info.BookInfoComponentWrapper
import ru.plovotok.testkmpapp.presentation.info.DefaultBookInfoComponentWrapper

@OptIn(ExperimentalDecomposeApi::class)
class DefaultHomeComponent(
    private val componentContext: ComponentContext,
    private val onFavorites: () -> Unit,
    override val weight: Value<Float>,
    private val onWeightChanged: (Float) -> Unit,
    private val onSaveWeight: () -> Unit
) : HomeComponent, ComponentContext by componentContext {

    private val navigation = PanelsNavigation<Unit, BookInfo, Unit>()

    private val _panels =
        childPanels(
            source = navigation,
            initialPanels = { Panels(main = Unit) },
            serializers = SERIALIZERS,
            handleBackButton = true,
            mainFactory = { _, ctx -> listComponent(ctx) },
            detailsFactory = ::detailsComponent,
            extraFactory = { _, _ -> },
        )

    override fun onBookInfo(id: Int) {
        navigation.navigate { state ->
            state.copy(details = BookInfo(BookPreview(id, null)))
        }
    }

    private fun listComponent(context: ComponentContext) =
        DefaultBookListComponent(
            componentContext = context,
            onBookClicked = {
                navigation.navigate { state ->
                    state.copy(details = BookInfo(it))
                }
            },
            onCloseDetails = {
                navigation.navigate { state ->
                    state.copy(details = null)
                }
            },
            onFavorites = onFavorites
        )

    private fun detailsComponent(
        info: BookInfo,
        ctx: ComponentContext
    ) = DefaultBookInfoComponentWrapper(
        componentContext = ctx,
        preview = info.preview,
        goBack = this::onBack
    )


    override val panels: Value<ChildPanels<*, BookListComponent, *, BookInfoComponentWrapper, *, *>> = _panels
    override fun onWeightChange(newWeight: Float) = onWeightChanged(newWeight)
    override fun saveNewWeight() = onSaveWeight()

    override fun setMode(mode: ChildPanelsMode) {
        navigation.navigate { state ->
            state.copy(
                details = state.takeIf { mode == ChildPanelsMode.DUAL }?.details?.preview?.let { BookInfo(preview = it) } ?: state.details,
                mode = mode
            )
        }
    }

    override fun onBack() {
        navigation.pop()
    }

    private companion object {
        private val SERIALIZERS = Triple(Unit.serializer(), BookInfo.serializer(), Unit.serializer())
    }

    @Serializable
    private data class BookInfo(val preview: BookPreview)
}