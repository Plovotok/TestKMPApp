package ru.plovotok.testkmpapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.plovotok.testkmpapp.presentation.description
import ru.plovotok.testkmpapp.presentation.filters.SearchFilterContent
import ru.plovotok.testkmpapp.presentation.isInternetError
import ru.plovotok.testkmpapp.presentation.ui.BaseScreen
import ru.plovotok.testkmpapp.presentation.ui.BookTopBar
import ru.plovotok.testkmpapp.presentation.ui.colorScheme
import ru.plovotok.testkmpapp.presentation.ui.components.BookListItem
import ru.plovotok.testkmpapp.presentation.ui.components.screens.EmptyScreen
import ru.plovotok.testkmpapp.presentation.ui.components.screens.NoInternetScreen
import ru.plovotok.testkmpapp.presentation.ui.components.scroll_bar.PlatformScrollController
import ru.plovotok.testkmpapp.presentation.ui.components.text_field.CloseCircleIconButton
import ru.plovotok.testkmpapp.presentation.ui.components.text_field.SearchInputText

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

        val query by component.query.subscribeAsState()

        val shouldShowSearchFieldInError by remember {
            derivedStateOf {
                state.query.isNotBlank() || activeGenres.isNotEmpty()
            }
        }

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

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = it.calculateTopPadding())
                    ) {
                        if (shouldShowSearchFieldInError) {
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
                                trailingIcon = {
                                    CloseCircleIconButton(
                                        visible = query.isNotEmpty(),
                                        onClick = {
                                            component.onQueryChanged("")
                                            component.getBooks("")
                                        }
                                    )
                                },
                                onSearch = {
                                    component.getBooks(query)
                                }
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){

                            if (isInternetError) {
                                NoInternetScreen(
                                    onRefresh = component::retry
                                )
                            } else {
                                Text(
                                    text = state.refreshError!!.description()
                                        ?: "Something went wrong :(",
                                    modifier = Modifier.padding(horizontal = 40.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                else -> {
                    if (state.books.isNotEmpty()) {

                        val listState = rememberLazyListState()

                        LaunchedEffect(state.books) {
                            snapshotFlow {
                                (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                                    ?: -1) >= state.books.lastIndex - 4
                            }.distinctUntilChanged().collect { shouldLoad ->
                                if (shouldLoad) {
                                    component.loadNext()
                                }
                            }
                        }

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
                                        trailingIcon = {
                                            CloseCircleIconButton(
                                                visible = query.isNotEmpty(),
                                                onClick = {
                                                    component.onQueryChanged("")
                                                    component.getBooks("")
                                                }
                                            )
                                        },
                                        onSearch = {
                                            component.getBooks(query)
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

                            PlatformScrollController(
                                listState = listState,
                                topPadding = it.calculateTopPadding(),
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = it.calculateTopPadding())
                        ) {
                            if (shouldShowSearchFieldInError) {
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
                                    trailingIcon = {
                                        CloseCircleIconButton(
                                            visible = query.isNotEmpty(),
                                            onClick = {
                                                component.onQueryChanged("")
                                                component.getBooks("")
                                            }
                                        )
                                    },
                                    onSearch = {
                                        component.getBooks(query)
                                    }
                                )
                            }

                            EmptyScreen(
                                title = "Not found",
                                description = "Book \"${state.query}\" is not found.",
                                modifier = Modifier.padding(horizontal = 40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}