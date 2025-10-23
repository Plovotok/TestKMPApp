package com.example.testkmpapp.main

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MainViewModel: InstanceKeeper.Instance, KoinComponent {

    val uiState = MutableValue(MainComponent.UiState())

    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    init {
        getPromos()
    }

    fun getPromos() {
        viewModelScope.launch {
            uiState.update { it.copy(isLoading = true) }
            delay(5000)
            val items = (1..100).map {
                "Promo $it"
            }
            uiState.update {
                it.copy(
                    isLoading = false,
                    items = items
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModelScope.cancel()
    }
}