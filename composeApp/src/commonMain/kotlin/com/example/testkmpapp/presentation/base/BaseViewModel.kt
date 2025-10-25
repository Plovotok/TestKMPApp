package com.example.testkmpapp.presentation.base

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseViewModel: InstanceKeeper.Instance {

    protected val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    override fun onDestroy() {
        super.onDestroy()
        viewModelScope.cancel()
    }
}

inline fun <reified T: BaseViewModel> ComponentContext.getViewModel(
    factory: () -> T
) = instanceKeeper.getOrCreate { factory() }