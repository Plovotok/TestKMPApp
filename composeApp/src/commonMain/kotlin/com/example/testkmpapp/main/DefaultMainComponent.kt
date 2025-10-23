package com.example.testkmpapp.main

import com.arkivanov.decompose.ComponentContext

class DefaultMainComponent(
    private val componentContext: ComponentContext,
    private val onShowWelcome: () -> Unit
) : MainComponent, ComponentContext by componentContext {

    override fun onShowWelcomeClicked() = onShowWelcome()

}