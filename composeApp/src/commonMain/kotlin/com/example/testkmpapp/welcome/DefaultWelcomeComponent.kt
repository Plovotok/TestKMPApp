package com.example.testkmpapp.welcome

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.testkmpapp.Platform
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultWelcomeComponent(
    override val promo: String,
    private val componentContext: ComponentContext,
    private val onFinished: () -> Unit,
) : WelcomeComponent, ComponentContext by componentContext, KoinComponent {

    private val platform: Platform by inject()
    private val state = MutableValue(WelcomeComponent.Model())

    override val model: Value<WelcomeComponent.Model> = state
    override fun onLoginChange(newValue: String) {
        state.update { it.copy(login = newValue) }
    }

    override fun onPasswordChange(newPassword: String) {
        state.update { it.copy(password = newPassword) }
    }

    override fun onRegister() {

    }

    override fun onBackClicked() {
        onFinished()
    }
}