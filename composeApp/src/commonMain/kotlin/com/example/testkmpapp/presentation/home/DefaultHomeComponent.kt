package com.example.testkmpapp.presentation.home

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
import com.example.testkmpapp.domain.models.BookPreview
import com.example.testkmpapp.presentation.info.BookInfoComponent
import com.example.testkmpapp.presentation.info.DefaultBookInfoComponent
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

@OptIn(ExperimentalDecomposeApi::class)
class DefaultHomeComponent(
    private val componentContext: ComponentContext,
    private val onFavorites: () -> Unit
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
    ) = DefaultBookInfoComponent(
        componentContext = ctx,
        preview = info.preview,
        onGoBack = {
            navigation.pop()
        }
    )


    override val panels: Value<ChildPanels<*, BookListComponent, *, BookInfoComponent, *, *>> = _panels

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