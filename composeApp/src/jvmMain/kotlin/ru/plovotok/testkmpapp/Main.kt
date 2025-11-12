package ru.plovotok.testkmpapp

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jxmapviewer.JXMapKit
import org.jxmapviewer.VirtualEarthTileFactoryInfo
import org.jxmapviewer.cache.FileBasedLocalCache
import org.jxmapviewer.viewer.DefaultTileFactory
import org.jxmapviewer.viewer.GeoPosition
import org.jxmapviewer.viewer.TileFactoryInfo
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
import java.io.File
import java.lang.System.setProperty
import java.util.Locale
import javax.swing.UIManager
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sinh


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

    if (Desktop.getDesktop().isSupported(Desktop.Action.APP_OPEN_URI)) {
        Desktop.getDesktop().setOpenURIHandler { uri ->
            DeeplinkHelper.handleDeepLink(uri.uri.toString())
        }
    }

    try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName())
    } catch (_: Throwable) {

    }

    application {
        val windowState = rememberWindowState(
            size = DpSize(1000.dp, 640.dp)
        )

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

            SwingPanel(
                background = Color.Yellow,
                factory = {
                    // Create a TileFactoryInfo for OpenStreetMap
//                    val info: TileFactoryInfo = OSMTileFactoryInfo("OpenStreetMap", "https://tile.openstreetmap.org")
                    val info: TileFactoryInfo = VirtualEarthTileFactoryInfo(
                        VirtualEarthTileFactoryInfo.MAP
                    )
                    val tileFactory: DefaultTileFactory = DefaultTileFactory(info)
                    tileFactory.apply {
                        setLocalCache(FileBasedLocalCache(File(System.getProperty("java.io.tmpdir"), "tiles"), true))
                        setThreadPoolSize(8)
                    }
                    // Set the focus
                    val moscow: GeoPosition = GeoPosition(55.7569, 37.6151)
                    JXMapKit().apply {
                        setTileFactory(tileFactory)
                        addressLocation = moscow
                        setZoom(12)
                    }
                },
                modifier = Modifier.size(800.dp).clip(CircleShape)
            )

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

class DgisTileFactoryInfo(
    private val key: String
): TileFactoryInfo(
    "Dgis",
    1, 18, 19,
    256, true, true,
    "https://static.maps.2gis.com/1.0",
    "x", "y", "z"
) {


    override fun getTileUrl(x: Int, y: Int, zoom: Int): String? {
        val zoom = totalMapZoom - zoom

        val xtile = x + 0.5
        val ytile = y + 0.5

        val n = 2.0.pow(zoom.toDouble())
        val lon_deg = ((xtile / n) * 360.0) - 180.0
        val lat_rad = atan(sinh(Math.PI * (1 - 2 * ytile / n)))
        val lat_deg = (lat_rad * 180.0) / Math.PI


        val url = (baseURL
                + "?s=256x256"
                + "&z=" + zoom
                + "&c=" + lat_deg + "," + lon_deg
                + "&key=" + key)

        return url
    }
}