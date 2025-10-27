package com.example.testkmpapp.presentation.ui.components.text_field

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.testkmpapp.presentation.ui.colorScheme

@Composable
fun BaseInputText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground),
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    shape: Shape = RoundedCornerShape(12.dp),
    backgroundColor: Color = colorScheme.textFieldBackground,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    onFocusChanged: (Boolean) -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp),
    minHeight: Dp = 40.dp,
) {
    Row(
        modifier = modifier
            .defaultMinSize(
                minHeight = minHeight,
                minWidth = 40.dp,
            )
            .graphicsLayer {
                alpha = if (enabled) 1f else 0.5f
            }
            .background(backgroundColor, shape)
            .padding(paddingValues),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colorScheme.lightGrayTinted
        ) {
            leadingIcon?.let { it() }
            BaseTextInputLayout(
                value = value,
                isError = isError,
                enabled = enabled,
                maxLines = maxLines,
                singleLine = singleLine,
                interactionSource = interactionSource,
                visualTransformation = visualTransformation,
                readOnly = readOnly,
                textStyle = textStyle,
                onValueChange = onValueChange,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                modifier = Modifier
                    .weight(1f)
                    .focusable()
                    .focusRequester(focusRequester)
                    .onFocusChanged { onFocusChanged(it.isFocused) },
                placeholder = {
                    placeholder?.invoke()
                },
            )
            trailingIcon?.let { it() }
        }
    }
}


@Composable
fun BaseInputText(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground),
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    shape: Shape = RoundedCornerShape(12.dp),
    backgroundColor: Color = colorScheme.textFieldBackground,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    onFocusChanged: (Boolean) -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp),
    minHeight: Dp = 40.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    leadingIconAlignment: Alignment = Alignment.Center,
    trailingIconAlignment: Alignment = Alignment.Center
) {
    Row(
        modifier = modifier
            .defaultMinSize(
                minHeight = minHeight,
                minWidth = 40.dp,
            )
            .height(IntrinsicSize.Max)
            .graphicsLayer {
                alpha = if (enabled) 1f else 0.5f
            }
            .background(backgroundColor, shape)
            .padding(paddingValues),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colorScheme.lightGrayTinted
        ) {
            leadingIcon?.let {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = leadingIconAlignment
                ) {
                    it()
                }
            }
            BaseTextInputLayout(
                value = value,
                isError = isError,
                enabled = enabled,
                maxLines = maxLines,
                singleLine = singleLine,
                interactionSource = interactionSource,
                onFocusChanged = onFocusChanged,
                visualTransformation = visualTransformation,
                readOnly = readOnly,
                textStyle = textStyle,
                onValueChange = onValueChange,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                modifier = Modifier
                    .weight(1f)
                    .focusable()
                    .focusRequester(focusRequester),
                placeholder = {
                    placeholder?.invoke()
                },
            )
            trailingIcon?.let {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = trailingIconAlignment
                ) {
                    it()
                }
            }
        }
    }
}