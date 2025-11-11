package ru.plovotok.testkmpapp.presentation.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.testkmpapp.presentation.ui.theme.colorScheme

@Composable
fun BookListItem(
    book: BookPreview,
    onClick: () -> Unit,
    trailingContent: @Composable (() -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors(),
    modifier: Modifier = Modifier
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    ListItem(
        headlineContent = {
            Text(
                text = book.title ?: "-",
                maxLines = 2,
                overflow = TextOverflow.StartEllipsis
            )
        },
        supportingContent = {
            book.subTitle?.let {
                Text(text = it, maxLines = 1)
            }
        },
        leadingContent = {
            AsyncImage(
                model = book.image,
                contentDescription = book.title,
                modifier = Modifier.size(40.dp)
            )
        },
        colors = colors.copy(
            containerColor = if (isHovered || isPressed) colorScheme.semiLightGrayTinted.copy(alpha = 0.4f) else colors.containerColor
        ),
        trailingContent = trailingContent,
        modifier = modifier
            .height(64.dp)
            .clickable(
                interactionSource = interactionSource,
            ) {
                onClick()
            }
    )
}