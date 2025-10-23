package com.example.testkmpapp.root

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
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.essenty.backhandler.BackHandler
import com.example.testkmpapp.main.MainContent
import com.example.testkmpapp.ui.AppColorScheme
import com.example.testkmpapp.ui.LocalAppScheme
import com.example.testkmpapp.welcome.WelcomeContent

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
                        is RootComponent.Child.Main -> MainContent(component = instance.component)
                        is RootComponent.Child.Welcome -> WelcomeContent(component = instance.component)
                    }
                }
            }
        }
    }
}

expect fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T>

fun lightColorScheme() = AppColorScheme(
    primary = Color.Blue,
    background = Color.White,
    onBackground = Color.Black
)

fun darkColorScheme() = AppColorScheme(
    primary = Color.Blue,
    background = Color.Black,
    onBackground = Color.White
)