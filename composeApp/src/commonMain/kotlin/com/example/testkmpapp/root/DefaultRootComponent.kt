package com.example.testkmpapp.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.testkmpapp.main.DefaultMainComponent
import com.example.testkmpapp.main.MainComponent
import com.example.testkmpapp.welcome.DefaultWelcomeComponent
import com.example.testkmpapp.welcome.WelcomeComponent
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    private val ctx: ComponentContext
) : RootComponent, ComponentContext by ctx {

    private val navigation = StackNavigation<Config>()


    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Main,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(config: Config, childComponentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Main -> RootComponent.Child.Main(mainComponent(childComponentContext))
            is Config.Welcome -> RootComponent.Child.Welcome(welcomeComponent(config.promo, childComponentContext))
        }

    private fun mainComponent(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            onShowWelcome = {
                navigation.pushNew(Config.Welcome(it))
            }
        )

    private fun welcomeComponent(promo: String, componentContext: ComponentContext): WelcomeComponent =
        DefaultWelcomeComponent(
            promo = promo,
            componentContext = componentContext,
            onFinished = navigation::pop,
        )

    override fun onBack() {
        navigation.pop()
    }

    @Serializable
    sealed interface Config {

        @Serializable
        data object Main: Config

        @Serializable
        data class Welcome(val promo: String): Config
    }

}