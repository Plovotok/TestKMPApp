package ru.plovotok.testkmpapp.presentation.ui.components.scroll_bar

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun BoxScope.PlatformScrollController(
    listState: LazyListState,
    reverseLayout: Boolean,
    modifier: Modifier,
    topPadding: Dp,
    bottomPadding: Dp
) {
    ScrollToTopButton(
        scrollState = listState,
        verticalPadding = 16.dp + bottomPadding,
    )
}