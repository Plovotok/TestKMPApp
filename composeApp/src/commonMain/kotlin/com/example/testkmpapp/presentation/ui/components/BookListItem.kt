package com.example.testkmpapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.testkmpapp.domain.models.BookPreview

@Composable
fun BookListItem(
    book: BookPreview,
    onClick: () -> Unit,
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
        modifier = modifier.clickable {
            onClick()
        }
    )
}