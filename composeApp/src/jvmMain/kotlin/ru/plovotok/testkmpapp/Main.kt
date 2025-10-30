package ru.plovotok.testkmpapp

import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import ru.plovotok.testkmpapp.di.appModule
import ru.plovotok.testkmpapp.presentation.root.DefaultRootComponent
import ru.plovotok.testkmpapp.presentation.root.RootContent
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import testkmpapp.composeapp.generated.resources.Res
import testkmpapp.composeapp.generated.resources.app_icon
import java.lang.System.setProperty

fun main() {

    startKoin {
        modules(appModule())
    }

    val lifecycle = LifecycleRegistry()

    val root =
        runOnUiThread {
            DefaultRootComponent(
                ctx = DefaultComponentContext(
                    lifecycle = lifecycle,
                ),
            )
        }

    setProperty("apple.awt.application.name", "Books")

    application {
        val windowState = rememberWindowState()

        Window(
            icon = painterResource(Res.drawable.app_icon),
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Books"
        ) {
            LifecycleController(
                lifecycleRegistry = lifecycle,
                windowState = windowState,
                windowInfo = LocalWindowInfo.current,
            )

            RootContent(root)
        }
    }
}
