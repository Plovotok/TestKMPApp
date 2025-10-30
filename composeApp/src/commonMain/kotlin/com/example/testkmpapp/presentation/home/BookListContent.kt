package com.example.testkmpapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.testkmpapp.presentation.description
import com.example.testkmpapp.presentation.filters.SearchFilterContent
import com.example.testkmpapp.presentation.isInternetError
import com.example.testkmpapp.presentation.ui.BaseScreen
import com.example.testkmpapp.presentation.ui.BookTopBar
import com.example.testkmpapp.presentation.ui.colorScheme
import com.example.testkmpapp.presentation.ui.components.BookListItem
import com.example.testkmpapp.presentation.ui.components.buttons.ScrollToTopButton
import com.example.testkmpapp.presentation.ui.components.screens.NoInternetScreen
import com.example.testkmpapp.presentation.ui.components.text_field.SearchInputText
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListContent(
    component: BookListComponent,
    activeBookId: Int?,
    modifier: Modifier = Modifier
) {
    val state by component.state.subscribeAsState()

    val searchConfiguration by component.filterDialog.subscribeAsState()

    searchConfiguration.child?.let {
        SearchFilterContent(it.instance)
    }

    val activeGenres by component.currentGenres.subscribeAsState()

    BaseScreen(
        topBar = {
            BookTopBar(
                title = { Text(text = "Books") },
                actions = {
                    BadgedBox(
                        badge = {
                            if (state.favorites.isNotEmpty()) {
                                Badge(
                                    modifier = Modifier.padding(end = 16.dp)
                                ) {
                                    Text(state.favorites.size.toString())
                                }
                            }
                        },
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        IconButton(
                            onClick = {
                                component.openFavorites()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.BookmarkBorder,
                                contentDescription = "Go to favorites",
                            )
                        }
                    }
                },
                windowInsets = TopAppBarDefaults.windowInsets.only(WindowInsetsSides.Vertical + WindowInsetsSides.Start)
            )
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Vertical + WindowInsetsSides.Start),
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

                    val query by component.query.subscribeAsState()

                    Box {

                        LazyColumn(
                            contentPadding = it,
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                SearchInputText(
                                    text = query,
                                    onTextChange = component::onQueryChanged,
                                    hint = "eg. Harry Potter",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp)
                                        .padding(
                                            vertical = 6.dp
                                        ),
                                    isEnabled = false,
                                    trailingContent = {
                                        IconButton(
                                            onClick = component::showFiltersDialog,
                                            modifier = Modifier.padding(end = 16.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.FilterList,
                                                contentDescription = "Filters",
                                                modifier = Modifier.size(24.dp),
                                                tint = if (activeGenres.isNotEmpty()) colorScheme.primary else colorScheme.onBackground
                                            )
                                        }
                                    },
                                    onSearch = {

                                    }
                                )
                            }
                            items(state.books) { book ->
                                val isFavorite by remember {
                                    derivedStateOf {
                                        state.favorites.contains(book.id)
                                    }
                                }
                                BookListItem(
                                    book = book,
                                    trailingContent = {
                                        Box(
                                            modifier = Modifier.size(48.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (isFavorite) {
                                                IconButton(
                                                    onClick = {
                                                        component.removeBookFromFavorites(book)
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Bookmark,
                                                        contentDescription = "Add to favorite",
                                                        tint = colorScheme.primary
                                                    )
                                                }
                                            }
                                        }
                                    },
                                    colors = ListItemDefaults.colors(
                                        containerColor = if (book.id == activeBookId) colorScheme.semiLightGrayTinted.copy(
                                            alpha = 0.4f
                                        ) else Color.Unspecified
                                    ),
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

                        ScrollToTopButton(
                            scrollState = listState,
                            verticalPadding = it.calculateBottomPadding() + 16.dp
                        )
                    }
                }
            }
        }
    }
}