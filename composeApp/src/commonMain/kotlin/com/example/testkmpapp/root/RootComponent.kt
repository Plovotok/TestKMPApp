package com.example.testkmpapp.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.testkmpapp.main.MainComponent
import com.example.testkmpapp.welcome.WelcomeComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBack()

    sealed class Child() {
        class Main(val component: MainComponent): RootComponent.Child()
        class Welcome(val component: WelcomeComponent): RootComponent.Child()
    }
}