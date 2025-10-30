package com.example.testkmpapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.example.testkmpapp.presentation.root.RootComponent
import com.example.testkmpapp.presentation.root.RootContent

@OptIn(ExperimentalDecomposeApi::class)
@Suppress("unused") // Used in Swift
fun RootViewController(root: RootComponent, backDispatcher: BackDispatcher) = ComposeUIViewController {
    PredictiveBackGestureOverlay(
        backDispatcher = backDispatcher,
        backIcon = null,
        endEdgeEnabled = false,
        edgeWidth = 24.dp,
        modifier = Modifier.fillMaxSize()
    ) {
        RootContent(root, modifier = Modifier.fillMaxSize())
    }
}