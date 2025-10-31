package ru.plovotok.testkmpapp.presentation.ui.components.scroll_bar

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
expect fun BoxScope.PlatformScrollController(
    listState: LazyListState,
    reverseLayout: Boolean = false,
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp
)