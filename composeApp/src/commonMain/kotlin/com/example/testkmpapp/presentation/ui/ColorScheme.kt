package com.example.testkmpapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalAppScheme = compositionLocalOf<AppColorScheme> {
    error("AppColorScheme not initialized")
}

val colorScheme
    @Composable
    get() = LocalAppScheme.current

data class AppColorScheme(
    val background: Color,
    val onBackground: Color,
    val primary: Color,
    val error: Color,
    val lightGrayTinted: Color,
    val semiLightGrayTinted: Color,
    val textFieldBackground: Color,
    val sheetColor: Color,
    val surface2: Color
)