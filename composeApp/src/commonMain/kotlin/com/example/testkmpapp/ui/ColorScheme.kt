package com.example.testkmpapp.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalAppScheme = compositionLocalOf<AppColorScheme> {
    error("AppColorScheme not initialized")
}

data class AppColorScheme(
    val background: Color,
    val onBackground: Color,
    val primary: Color,
)