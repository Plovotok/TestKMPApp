package com.example.testkmpapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.example.testkmpapp.root.RootComponent
import com.example.testkmpapp.root.RootContent

fun RootViewController(root: RootComponent) = ComposeUIViewController {
    RootContent(root, modifier = Modifier.fillMaxSize())
}