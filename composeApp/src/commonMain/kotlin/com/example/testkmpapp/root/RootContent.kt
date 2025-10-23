package com.example.testkmpapp.root

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.testkmpapp.main.MainContent
import com.example.testkmpapp.ui.AppColorScheme
import com.example.testkmpapp.ui.LocalAppScheme
import com.example.testkmpapp.welcome.WelcomeContent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(
        LocalAppScheme provides lightColorScheme()
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
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars),
                color = Color.White
            ) {
                Children(
                    stack = component.stack,
                    modifier = Modifier.fillMaxSize(),
                    animation = stackAnimation(fade() + slide())
                ) {
                    when (val instance = it.instance) {
                        is RootComponent.Child.Main -> MainContent(component = instance.component)
                        is RootComponent.Child.Welcome -> WelcomeContent(component = instance.component)
                    }
                }
            }
        }
    }
}

fun lightColorScheme() = AppColorScheme(
    primary = Color.Blue,
    background = Color.White,
    onBackground = Color.Black
)