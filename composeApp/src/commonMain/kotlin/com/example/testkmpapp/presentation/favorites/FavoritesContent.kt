package com.example.testkmpapp.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.testkmpapp.presentation.ui.BaseScreen
import com.example.testkmpapp.presentation.ui.components.BookListItem
import com.example.testkmpapp.presentation.ui.components.icons.BackButton
import com.example.testkmpapp.presentation.ui.components.screens.EmptyScreen
import com.example.testkmpapp.presentation.ui.components.text_field.SearchInputText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesContent(
    component: FavoritesComponent
) {

    val state by component.state.subscribeAsState()

    BaseScreen(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    BackButton(onClick = component::onBackClicked)
                },
                title = {
                    Text(text = "Favorites")
                }
            )
        }
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
                        .padding(
                            top = it.calculateTopPadding()
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
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = it.calculateBottomPadding()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(state.filtered) {
                                BookListItem(
                                    book = it,
                                    onClick = {
                                        component.onBookClicked(it)
                                    }
                                )
                            }
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