package ru.plovotok.testkmpapp.presentation.root

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.launch
import ru.plovotok.shared.domain.AppPreferences
import ru.plovotok.testkmpapp.presentation.base.BaseViewModel

class RootViewModel(
    private val settings: AppPreferences
): BaseViewModel() {

    val leftPanelState: MutableValue<Float> = MutableValue(0.45f)

    init {
        settings.getFloatData("DEFAULT_PANEL_WEIGHT").collectInViewModel { value ->
            if (value != null) {
                leftPanelState.update { value }
            }
        }
    }

    fun onWeightChange(weight: Float) {
        leftPanelState.update { weight }
    }

    fun saveCurrentWeight() {
        println("save weight")
        viewModelScope.launch {
            settings.setFloat("DEFAULT_PANEL_WEIGHT", leftPanelState.value)
        }
    }
}