package com.example.testkmpapp.presentation.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.testkmpapp.presentation.ui.colorScheme
import com.example.testkmpapp.presentation.ui.components.buttons.PrimaryButton
import com.example.testkmpapp.presentation.ui.components.text_field.SearchInputText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterContent(
    component: SearchFiltersComponent
) {
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    suspend fun dismiss() {
        state.hide()
        component.dismiss()
    }

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = component::dismiss,
        contentWindowInsets = { WindowInsets(0.dp) },
        containerColor = colorScheme.sheetColor,
        sheetState = state
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val query by component.query.subscribeAsState()
            SearchInputText(
                text = query,
                onTextChange = component::onQueryChanged,
                hint = "Fantasy",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 6.dp)
            )

            val state by component.state.subscribeAsState()

            val insets = WindowInsets.navigationBars.asPaddingValues()

            if (state.items.isNotEmpty()) {
                Scaffold(
                    bottomBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(colorScheme.background)
                        ) {
                            PrimaryButton(
                                text = "Apply",
                                onClick = {
                                    component.setNewGenres(state.selectedItems)
                                    scope.launch {
                                        dismiss()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .padding(bottom = insets.calculateBottomPadding())
                            )
                        }
                    }
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = it.calculateBottomPadding() + 8.dp
                            ),
                        columns = GridCells.Adaptive(160.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        items(state.items) {
                            val isSelected = state.selectedItems.contains(it)
                            ListItem(
                                leadingContent = {
                                    Box(modifier = Modifier.size(32.dp)) {
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Active",
                                                modifier = Modifier.size(22.dp),
                                                tint = colorScheme.primary
                                            )
                                        }
                                    }
                                },
                                headlineContent = {
                                    Text(
                                        text = it.displayName,
                                        fontSize = 18.sp
                                    )
                                },
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.Transparent
                                ),
                                modifier = Modifier.clickable {
                                    component.onGenreSelectChange(it)
                                }
                            )
                        }
                    }
                }
            } else {
                if (state.query.isNotBlank()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Genre \"${state.query}\" not found."
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No genres."
                        )
                    }
                }
            }
        }
    }
}