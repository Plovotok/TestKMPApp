package com.example.testkmpapp.welcome

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.testkmpapp.getPlatform

class DefaultWelcomeComponent(
    private val componentContext: ComponentContext,
    private val onFinished: () -> Unit
) : WelcomeComponent, ComponentContext by componentContext {

    private val state = MutableValue(WelcomeComponent.Model())
    override val model: Value<WelcomeComponent.Model> = state

    override fun onUpdateGreetingText() {
        state.update { it.copy(greetingText = "Welcome from ${ getPlatform().name }") }
    }

    override fun onBackClicked() {
        onFinished()
    }
}