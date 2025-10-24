package com.example.testkmpapp.presentation.info

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.testkmpapp.presentation.ui.BaseScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookInfoContent(
    component: BookInfoComponent,
    modifier: Modifier = Modifier
) {
    val model by component.state.subscribeAsState()

    BaseScreen(
        topBar = {
            TopAppBar(
                title = { Text(text = "Welcome Screen") },
                navigationIcon = {
                    TextButton(onClick = component::onBack) {
                        Text(text = "Back")
                    }
                },
            )
        },
        modifier = modifier
    ) {

    }
}