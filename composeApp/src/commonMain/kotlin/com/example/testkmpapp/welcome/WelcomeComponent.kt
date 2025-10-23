package com.example.testkmpapp.welcome

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

interface WelcomeComponent {

    val promo: String
    val model: Value<Model>

    fun onLoginChange(newValue: String)
    fun onPasswordChange(newPassword: String)

    fun onRegister()
    fun onBackClicked()

    data class Model(
        val login: String = "",
        val password: String = ""
    )
}