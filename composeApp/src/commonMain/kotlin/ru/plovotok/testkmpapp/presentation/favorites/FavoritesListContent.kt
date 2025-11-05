package ru.plovotok.testkmpapp.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.plovotok.testkmpapp.presentation.ui.BaseScreen
import ru.plovotok.testkmpapp.presentation.ui.BookTopBar
import ru.plovotok.testkmpapp.presentation.ui.colorScheme
import ru.plovotok.testkmpapp.presentation.ui.components.BookListItem
import ru.plovotok.testkmpapp.presentation.ui.components.icons.BackButton
import ru.plovotok.testkmpapp.presentation.ui.components.screens.EmptyScreen
import ru.plovotok.testkmpapp.presentation.ui.components.scroll_bar.PlatformScrollController
import ru.plovotok.testkmpapp.presentation.ui.components.text_field.SearchInputText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesListContent(
    component: FavoritesListComponent,
    activeBookId: Int?,
    modifier: Modifier = Modifier
) {

    val state by component.state.subscribeAsState()

    val scrollState = rememberLazyListState()

    BaseScreen(
        topBar = {
            BookTopBar(
                navigationIcon = {
                    BackButton(onClick = component::onBackClicked)
                },
                title = {
                    Text(text = "Favorites")
                },
                windowInsets = TopAppBarDefaults.windowInsets.only(WindowInsetsSides.Vertical + WindowInsetsSides.Start),
            )
        },
        modifier = modifier,
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Vertical + WindowInsetsSides.Start),
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (state.totalItems > 0) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding())
                        .padding(
                            start = it.calculateStartPadding(LocalLayoutDirection.current),
                            end = it.calculateEndPadding(LocalLayoutDirection.current)
                        )
                ) {
                    val query by component.query.subscribeAsState()
                    SearchInputText(
                        text = query,
                        onTextChange = component::onQueryChanged,
                        hint = "eg. Harry Potter",
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (state.filtered.isNotEmpty()) {
                        Box {
                            LazyColumn(
                                state = scrollState,
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(bottom = it.calculateBottomPadding()),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(state.filtered, key = { it.id }) {
                                    BookListItem(
                                        book = it,
                                        onClick = {
                                            component.onBookClicked(it)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .animateItem(),
                                        colors = ListItemDefaults.colors(
                                            containerColor = if (it.id == activeBookId) colorScheme.semiLightGrayTinted.copy(
                                                alpha = 0.4f
                                            ) else Color.Unspecified
                                        ),
                                    )
                                }
                            }

                            PlatformScrollController(
                                listState = scrollState,
                            )
                        }
                    } else {
                        EmptyScreen(
                            title = "Not found",
                            description = "Book \"${state.query}\" is not found.",
                            modifier = Modifier.padding(horizontal = 40.dp)
                        )
                    }
                }
            } else {
                EmptyScreen(
                    title = "List is empty",
                    description = "You have not add any book yet.",
                    modifier = Modifier.padding(horizontal = 40.dp)
                )
            }
        }
    }
}