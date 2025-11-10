package ru.plovotok.testkmpapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ExperimentalDecomposeApi
import ru.plovotok.testkmpapp.presentation.root.RootComponent
import ru.plovotok.testkmpapp.presentation.root.RootContent

@OptIn(ExperimentalDecomposeApi::class)
@Suppress("unused") // Used in Swift
fun RootViewController(root: RootComponent) = ComposeUIViewController {
    RootContent(
        root,
        modifier = Modifier.fillMaxSize(),
    )
}