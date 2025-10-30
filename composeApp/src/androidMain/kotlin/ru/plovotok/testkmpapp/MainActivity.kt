package ru.plovotok.testkmpapp

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import ru.plovotok.testkmpapp.presentation.root.DefaultRootComponent
import ru.plovotok.testkmpapp.presentation.root.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        if (isCompactDevice()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }


        val root = DefaultRootComponent(defaultComponentContext())
        setContent {
            RootContent(root)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (isCompactDevice()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {

        }

    }

    private fun isCompactDevice(): Boolean {
        val metrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display?.getRealMetrics(metrics)
        } else {
            @Suppress("DEPRECATION")
            val display = (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(metrics)
        }

        val widthDp = metrics.widthPixels / metrics.density
        val heightDp = metrics.heightPixels / metrics.density
        val isCompact = (widthDp < 600) || (heightDp < 600)
        return isCompact
    }

}