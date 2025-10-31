package ru.plovotok.testkmpapp.presentation.ui.components.scroll_bar

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import ru.plovotok.testkmpapp.presentation.ui.colorScheme

@Composable
actual fun BoxScope.PlatformScrollController(
    listState: LazyListState,
    reverseLayout: Boolean,
    modifier: Modifier,
    topPadding: Dp,
    bottomPadding: Dp
) {

    val adapter = rememberScrollbarAdapter(listState)

    val style = LocalScrollbarStyle.current.copy(
        unhoverColor = colorScheme.onBackground.copy(alpha = 0.27f),
        hoverColor = colorScheme.onBackground.copy(alpha = 0.60f)
    )

    VerticalScrollbar(
        adapter = adapter,
        reverseLayout = reverseLayout,
        style = style,
        modifier = modifier
            .padding(
                top = topPadding,
                bottom = bottomPadding
            )
            .fillMaxHeight()
            .align(Alignment.CenterEnd)
    )
}