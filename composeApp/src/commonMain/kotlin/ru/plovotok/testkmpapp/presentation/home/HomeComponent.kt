package ru.plovotok.testkmpapp.presentation.home

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.plovotok.testkmpapp.presentation.info.BookInfoComponent

@OptIn(ExperimentalDecomposeApi::class)
interface HomeComponent: BackHandlerOwner {

    val panels: Value<ChildPanels<*, BookListComponent, *, BookInfoComponent, *, *>>

    fun setMode(mode: ChildPanelsMode)

    fun onBack()
}