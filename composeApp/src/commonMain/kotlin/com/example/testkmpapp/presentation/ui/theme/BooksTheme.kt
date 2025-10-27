package com.example.testkmpapp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.testkmpapp.presentation.ui.AppColorScheme
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

private fun lightColorScheme() = AppColorScheme(
    primary = Color(0xff0073ff),
    background = Color.White,
    onBackground = Color.Black,
    error = Color(192, 0, 23),
    lightGrayTinted = Color(100, 100, 100),
    semiLightGrayTinted = Color(125, 125, 125),
    textFieldBackground = Color(117, 117, 128, 31),
    sheetColor = Color.White
)

private fun darkColorScheme() = AppColorScheme(
    primary = Color(0xff0066ff),
    background = Color.Black,
    onBackground = Color.White,
    error = Color(233, 17, 32),
    lightGrayTinted = Color(165, 165, 165),
    semiLightGrayTinted = Color(140, 140, 140),
    textFieldBackground = Color(117, 117, 128, 61),
    sheetColor = Color(14, 14, 16)
)