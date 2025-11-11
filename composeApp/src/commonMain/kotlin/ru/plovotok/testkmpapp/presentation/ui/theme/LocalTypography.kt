package ru.plovotok.testkmpapp.presentation.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

val LocalTypography = staticCompositionLocalOf<BooksTypography> {
    error("Typography not initialized!")
}

@Stable
data class BooksTypography(
    val titleLarge: TextStyle,
    val screenDescription: TextStyle,
    val labelMedium: TextStyle
)