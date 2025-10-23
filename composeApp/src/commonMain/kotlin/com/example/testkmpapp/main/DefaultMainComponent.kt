package com.example.testkmpapp.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.Lifecycle

class DefaultMainComponent(
    private val onShowWelcome: (promo: String) -> Unit,
    private val componentContext: ComponentContext,
) : MainComponent, ComponentContext by componentContext {

    override fun onShowWelcomeClicked(promo: String) = onShowWelcome(promo)

    private val vm: MainViewModel = instanceKeeper.getOrCreate {
        MainViewModel()
    }

    override val uiState: Value<MainComponent.UiState> = vm.uiState

    init {
        lifecycle.subscribe(
            object: Lifecycle.Callbacks {
                override fun onCreate() {
                    super.onCreate()
                    println("Create")
                }

                override fun onStart() {
                    super.onStart()
                    println("Start")
                }

                override fun onResume() {
                    super.onResume()
                    println("Resume")
                }

                override fun onPause() {
                    super.onPause()
                    println("Pause")
                }

                override fun onStop() {
                    super.onStop()
                    println("Stop")
                }

                override fun onDestroy() {
                    super.onDestroy()
                    println("Destroy")
                }
            }
        )
    }

}