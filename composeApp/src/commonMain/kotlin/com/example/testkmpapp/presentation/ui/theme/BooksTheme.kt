package com.example.testkmpapp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.example.testkmpapp.presentation.root.darkColorScheme
import com.example.testkmpapp.presentation.root.lightColorScheme
import com.example.testkmpapp.presentation.ui.LocalAppScheme

@Composable
fun BooksTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppScheme provides if (isDark) darkColorScheme() else lightColorScheme()
    ) {
        val colors = LocalAppScheme.current

        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(
                primary = colors.primary,
                surface = colors.background,
                onSurface = colors.onBackground,
                onBackground = colors.onBackground,
                background = colors.background
            )
        ) {
            Surface(
                modifier = modifier,
                color = colors.background
            ) {
                content()
            }
        }
    }
}