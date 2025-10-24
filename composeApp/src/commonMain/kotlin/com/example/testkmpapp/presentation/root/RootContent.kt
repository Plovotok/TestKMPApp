package com.example.testkmpapp.presentation.root

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.example.testkmpapp.presentation.backAnimation
import com.example.testkmpapp.presentation.home.HomeContent
import com.example.testkmpapp.presentation.ui.AppColorScheme
import com.example.testkmpapp.presentation.ui.LocalAppScheme
import com.example.testkmpapp.presentation.info.BookInfoContent

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(
        LocalAppScheme provides if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
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
                modifier = modifier
                    .fillMaxSize(),
                color = colors.background
            ) {
                Children(
                    stack = component.stack,
                    modifier = Modifier.fillMaxSize(),
                    animation = backAnimation(component.backHandler, component::onBack)
                ) {
                    when (val instance = it.instance) {
                        is RootComponent.Child.Home -> HomeContent(component = instance.component)
                        is RootComponent.Child.BookInfo -> BookInfoContent(component = instance.component)
                    }
                }
            }
        }
    }
}

fun lightColorScheme() = AppColorScheme(
    primary = Color(0xff0073ff),
    background = Color.White,
    onBackground = Color.Black
)

fun darkColorScheme() = AppColorScheme(
    primary = Color(0xff0066ff),
    background = Color.Black,
    onBackground = Color.White
)