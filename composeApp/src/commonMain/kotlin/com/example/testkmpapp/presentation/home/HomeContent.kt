package com.example.testkmpapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.testkmpapp.presentation.description
import com.example.testkmpapp.presentation.isInternetError
import com.example.testkmpapp.presentation.ui.BaseScreen
import com.example.testkmpapp.presentation.ui.NoInternetScreen
import com.example.testkmpapp.presentation.ui.components.BookListItem
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.subscribeAsState()

    BaseScreen(
        topBar = {
            TopAppBar(
                title = { Text(text = "Books") },
            )
        },
        modifier = modifier
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when {
                state.isRefreshing -> {
                    CircularProgressIndicator()
                }

                state.refreshError != null -> {
                    val isInternetError = state.refreshError.isInternetError()

                    if (isInternetError) {
                        NoInternetScreen(
                            onRefresh = component::retry
                        )
                    } else {
                        Text(
                            text = state.refreshError!!.description() ?: "Something went wrong :(",
                            modifier = Modifier.padding(horizontal = 40.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {
                    val listState = rememberLazyListState()

                    LaunchedEffect(state.books) {
                        snapshotFlow {
                            (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1) >= state.books.lastIndex - 4
                        }.distinctUntilChanged().collect { shouldLoad ->
                            if (shouldLoad) {
                                component.loadNext()
                            }
                        }
                    }

                    LazyColumn(
                        contentPadding = it,
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(state.books) { book ->
                            BookListItem(
                                book = book,
                                onClick = {
                                    component.showBookInfo(book)
                                }
                            )
                        }
                        if (state.isAppending) {
                            item {
                                CircularProgressIndicator(modifier = Modifier.size(28.dp))
                            }
                        } else if (state.appendError != null) {
                            item {
                                TextButton(
                                    onClick = component::retry
                                ) {
                                    Text(text = "Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}