package com.example.testkmpapp.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.testkmpapp.ui.BaseScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    component: MainComponent,
    modifier: Modifier = Modifier
) {
    val state by component.uiState.subscribeAsState()
    BaseScreen(
        topBar = {
            TopAppBar(
                title = { Text(text = "Decompose Template") },
            )
        },
        modifier = modifier
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }
                state.error != null -> {
                    Text(
                        text = "Произошла ошибка",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
                else -> {
                    LazyColumn(
                        contentPadding = it
                    ) {
                        items(state.items) {
                            ListItem(
                                headlineContent = {
                                    Text(text = it)
                                },
                                modifier = Modifier
                                    .clickable {
                                        component.onShowWelcomeClicked(it)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}