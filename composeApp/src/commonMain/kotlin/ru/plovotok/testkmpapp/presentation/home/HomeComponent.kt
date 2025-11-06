package ru.plovotok.testkmpapp.presentation.home

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.plovotok.testkmpapp.presentation.info.BookInfoComponent
import ru.plovotok.testkmpapp.presentation.ui.components.ChildPanelsComponent

@OptIn(ExperimentalDecomposeApi::class)
interface HomeComponent: BackHandlerOwner, ChildPanelsComponent {

    val panels: Value<ChildPanels<*, BookListComponent, *, BookInfoComponent, *, *>>

    fun onBookInfo(id: Int)

    val weight: Value<Float>
    fun onWeightChange(newWeight: Float)

    fun saveNewWeight()

    fun onBack()

}