package com.example.testkmpapp.welcome

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.testkmpapp.Platform
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultWelcomeComponent(
    private val componentContext: ComponentContext,
    private val onFinished: () -> Unit
) : WelcomeComponent, ComponentContext by componentContext, KoinComponent {

    private val platform: Platform by inject()

    private val state = MutableValue(WelcomeComponent.Model())
    override val model: Value<WelcomeComponent.Model> = state

    override fun onUpdateGreetingText() {
        state.update { it.copy(greetingText = "Welcome from ${ platform.name }") }
    }

    override fun onBackClicked() {
        onFinished()
    }
}