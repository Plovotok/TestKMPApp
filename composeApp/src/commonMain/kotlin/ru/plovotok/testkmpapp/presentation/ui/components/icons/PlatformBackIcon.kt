package ru.plovotok.testkmpapp.presentation.ui.components.icons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

expect fun platformBackIcon(): ImageVector

@Composable
fun BackButton(
    onClick: () -> Unit,
    tint: Color = LocalContentColor.current
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = platformBackIcon(),
            contentDescription = "Back arrow",
            tint = tint
        )
    }
}