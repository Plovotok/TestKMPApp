package com.example.testkmpapp.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeContent(
    component: WelcomeComponent,
    modifier: Modifier = Modifier
) {
    val model by component.model.subscribeAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Welcome Screen") },
                navigationIcon = {
                    TextButton(onClick = component::onBackClicked) {
                        Text(text = "Back")
                    }
                },
            )
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { component.onUpdateGreetingText() },
            ) {
                Text(model.greetingText)
            }
        }
    }
}