package ru.plovotok.testkmpapp

import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.github.tkuenneth.nativeparameterstoreaccess.Dconf
import com.github.tkuenneth.nativeparameterstoreaccess.Dconf.HAS_DCONF
import com.github.tkuenneth.nativeparameterstoreaccess.MacOSDefaults
import com.github.tkuenneth.nativeparameterstoreaccess.NativeParameterStoreAccess.IS_MACOS
import com.github.tkuenneth.nativeparameterstoreaccess.NativeParameterStoreAccess.IS_WINDOWS
import com.github.tkuenneth.nativeparameterstoreaccess.WindowsRegistry
import com.sun.tools.javac.tree.TreeInfo.args
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import ru.plovotok.shared.di.appModule
import ru.plovotok.testkmpapp.presentation.DeeplinkHelper
import ru.plovotok.testkmpapp.presentation.di.viewModelsModule
import ru.plovotok.testkmpapp.presentation.root.DefaultRootComponent
import ru.plovotok.testkmpapp.presentation.root.RootContent
import ru.plovotok.testkmpapp.presentation.ui.LocalResizeIcon
import testkmpapp.composeapp.generated.resources.Res
import testkmpapp.composeapp.generated.resources.app_icon
import java.awt.Cursor
import java.awt.Desktop
import java.lang.System.setProperty
import java.util.Locale

@OptIn(DelicateCoroutinesApi::class)
fun main() {

    startKoin {
        modules(viewModelsModule)
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

    var isInDarkMode by mutableStateOf(isSystemInDarkTheme())

    GlobalScope.launch {
        while (isActive) {
            val newMode = isSystemInDarkTheme()
            if (isInDarkMode != newMode) {
                isInDarkMode = newMode
            }
            delay(1000)
        }
    }

    Desktop.getDesktop().setOpenURIHandler { uri ->
        DeeplinkHelper.handleDeepLink(uri.uri.toString())
    }

    application {
        val windowState = rememberWindowState()

        Window(
            icon = painterResource(Res.drawable.app_icon),
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Books"
        ) {
            CompositionLocalProvider(
                LocalResizeIcon provides PointerIcon(Cursor(Cursor.W_RESIZE_CURSOR))
            ) {

                LifecycleController(
                    lifecycleRegistry = lifecycle,
                    windowState = windowState,
                    windowInfo = LocalWindowInfo.current,
                )

                RootContent(root, isDark = isInDarkMode)
            }

        }
    }
}

fun isSystemInDarkTheme(): Boolean = when {
    IS_WINDOWS -> {
        val result = WindowsRegistry.getWindowsRegistryEntry(
            "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize",
            "AppsUseLightTheme")
        result == 0x0
    }
    IS_MACOS -> {
        val result = MacOSDefaults.getDefaultsEntry("AppleInterfaceStyle")
        result == "Dark"
    }
    HAS_DCONF -> {
        val result = Dconf.getDconfEntry("/org/gnome/desktop/interface/gtk-theme")
        result.lowercase(Locale.ROOT).contains("dark")
    }
    else -> false
}
