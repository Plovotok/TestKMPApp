package ru.plovotok.testkmpapp.presentation.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BooksTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColorScheme provides if (isDark) darkColorScheme() else lightColorScheme()
    ) {
        val colors = LocalColorScheme.current
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
                CompositionLocalProvider(
                    LocalIndication provides LocalIndication.current
                ) {
                    content()
                }
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
    sheetColor = Color.White,
    surface2 = Color(239, 239, 239),
    navigationColor = Color(0xfff9f9f9)
)

private fun darkColorScheme() = AppColorScheme(
    primary = Color(0xff0066ff),
    background = Color.Black,
    onBackground = Color.White,
    error = Color(233, 17, 32),
    lightGrayTinted = Color(165, 165, 165),
    semiLightGrayTinted = Color(140, 140, 140),
    textFieldBackground = Color(117, 117, 128, 61),
    sheetColor = Color(14, 14, 16),
    surface2 = Color(32, 32, 32, 255),
    navigationColor = Color(0xff0d0d0d)
)