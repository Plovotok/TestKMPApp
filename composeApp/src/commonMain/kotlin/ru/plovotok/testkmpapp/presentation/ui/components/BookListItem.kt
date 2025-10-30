package ru.plovotok.testkmpapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.plovotok.testkmpapp.domain.models.BookPreview

@Composable
fun BookListItem(
    book: BookPreview,
    onClick: () -> Unit,
    trailingContent: @Composable (() -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors(),
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = book.title,
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
                modifier = Modifier.size(64.dp)
            )
        },
        colors = colors,
        trailingContent = trailingContent,
        modifier = modifier.clickable {
            onClick()
        }
    )
}