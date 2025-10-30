package com.example.testkmpapp.presentation.favorites

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.example.testkmpapp.presentation.info.BookInfoComponent

@OptIn(ExperimentalDecomposeApi::class)
interface FavoritesComponent: BackHandlerOwner {

    val panels: Value<ChildPanels<*, FavoritesListComponent, *, BookInfoComponent, *, *>>

    fun setMode(mode: ChildPanelsMode)

    fun onBack()
}