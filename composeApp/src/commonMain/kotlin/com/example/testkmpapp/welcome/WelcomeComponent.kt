package com.example.testkmpapp.welcome

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

interface WelcomeComponent {

    val model: Value<Model>

    fun onUpdateGreetingText()
    fun onBackClicked()

    data class Model(
        val greetingText: String = "Welcome from Decompose!"
    )
}