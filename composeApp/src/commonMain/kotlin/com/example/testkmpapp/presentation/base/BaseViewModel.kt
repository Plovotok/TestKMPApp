package com.example.testkmpapp.presentation.base

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

abstract class BaseViewModel: InstanceKeeper.Instance {

    protected val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    protected fun <T> Flow<T>.collectInViewModel(
        collector: FlowCollector<T>
    ) {
        viewModelScope.launch {
            this@collectInViewModel.collect(collector)
        }
    }


    protected fun <T> (suspend () -> T).runInViewModelScope(
        onSuccess: suspend (T) -> Unit = {},
        onError: suspend (e: Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val result = this@runInViewModelScope()
                onSuccess(result)
            } catch (e: Throwable) {
                ensureActive()
                e.printStackTrace()
                onError(e)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelScope.cancel()
    }
}

inline fun <reified T: BaseViewModel> ComponentContext.getViewModel(
    factory: () -> T
) = instanceKeeper.getOrCreate { factory() }