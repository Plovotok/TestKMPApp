package ru.plovotok.testkmpapp.presentation.favorites

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.plovotok.testkmpapp.presentation.info.BookInfoComponent
import ru.plovotok.testkmpapp.presentation.ui.components.ChildPanelsComponent

@OptIn(ExperimentalDecomposeApi::class)
interface FavoritesComponent: BackHandlerOwner, ChildPanelsComponent {

    val weight: Value<Float>
    fun onWeightChange(newWeight: Float)

    fun onBookInfo(id: Int)

    val panels: Value<ChildPanels<*, FavoritesListComponent, *, BookInfoComponent, *, *>>

    fun onBack()
    fun saveNewWeight()
}