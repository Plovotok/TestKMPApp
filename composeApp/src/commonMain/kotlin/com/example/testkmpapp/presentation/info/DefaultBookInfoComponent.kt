package com.example.testkmpapp.presentation.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import org.koin.core.component.KoinComponent

class DefaultBookInfoComponent(
    private val componentContext: ComponentContext,
    private val id: Int,
    private val onGoBack: () -> Unit
) : BookInfoComponent, ComponentContext by componentContext, KoinComponent {

    override val state: Value<BookInfoComponent.BookState> = MutableValue(BookInfoComponent.BookState())

    override fun onBack() = onGoBack()
}