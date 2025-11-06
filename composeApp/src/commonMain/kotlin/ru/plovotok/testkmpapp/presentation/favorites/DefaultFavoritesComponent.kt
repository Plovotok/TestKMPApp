package ru.plovotok.testkmpapp.presentation.favorites

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
import ru.plovotok.testkmpapp.presentation.info.BookInfoComponent
import ru.plovotok.testkmpapp.presentation.info.DefaultBookInfoComponent

@OptIn(ExperimentalDecomposeApi::class)
class DefaultFavoritesComponent(
    private val componentContext: ComponentContext,
    private val onBack: () -> Unit,
    override val weight: Value<Float>,
    private val onWeightChanged: (Float) -> Unit,
    private val onSaveWeight: () -> Unit
): FavoritesComponent, ComponentContext by componentContext {

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
        DefaultFavoritesListComponent(
            componentContext = context,
            onInfo = {
                navigation.navigate { state ->
                    state.copy(details = BookInfo(it))
                }
            },
            onBack = onBack
        )

    private fun detailsComponent(
        info: BookInfo,
        ctx: ComponentContext
    ) = DefaultBookInfoComponent(
        componentContext = ctx,
        preview = info.preview,
        goBack = onBack
    )

    override fun onWeightChange(newWeight: Float) = onWeightChanged(newWeight)


    override val panels: Value<ChildPanels<*, FavoritesListComponent, *, BookInfoComponent, *, *>> = _panels

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

    override fun saveNewWeight() = onSaveWeight()

    private companion object {
        private val SERIALIZERS = Triple(Unit.serializer(), BookInfo.serializer(), Unit.serializer())
    }

    @Serializable
    private data class BookInfo(val preview: BookPreview)

}