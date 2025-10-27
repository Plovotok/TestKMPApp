package com.example.testkmpapp.presentation.ui.components.text_field

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.testkmpapp.presentation.ui.theme.BooksTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import testkmpapp.composeapp.generated.resources.Res
import testkmpapp.composeapp.generated.resources.ic_xmark_circle

@Composable
fun SearchInputText(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    hint: String,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    trailingIcon: (@Composable () -> Unit)? = null,
    trailingContent: @Composable RowScope.() -> Unit = {},
    onSearch: KeyboardActionScope.() -> Unit = {
        defaultKeyboardAction(imeAction = ImeAction.Done)
    },
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BaseInputText(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .weight(1f),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            },
            placeholder = {
                Text(text = hint, maxLines = 1, overflow = TextOverflow.Ellipsis)
            },
            interactionSource = interactionSource,
            isError = isError,
            enabled = isEnabled,
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = onSearch),
            focusRequester = focusRequester,
            trailingIcon = {
                if (trailingIcon != null) {
                    trailingIcon()
                } else {
                    CloseCircleIconButton(
                        visible = text.isNotEmpty(),
                        onClick = {
                            onTextChange("")
                        }
                    )
                }
            },
        )
        trailingContent()
    }
}

@Composable
fun CloseCircleIconButton(
    visible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = Color.Gray
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .clickable(onClick = onClick)
                .padding(4.dp)
                .size(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_xmark_circle),
                contentDescription = null,
                tint = tint
            )
        }
    }
}

@Preview
@Composable
private fun SearchInputTextPreview() {
    BooksTheme(
        isDark = true
    ) {
        SearchInputText(
            text = "",
            onTextChange = {},
            hint = "Search"
        )
    }
}