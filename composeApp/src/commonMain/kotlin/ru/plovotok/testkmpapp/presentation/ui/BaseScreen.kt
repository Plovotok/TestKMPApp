package ru.plovotok.testkmpapp.presentation.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.plovotok.testkmpapp.presentation.ui.theme.LocalColorScheme

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    containerColor: Color = LocalColorScheme.current.background,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable ColumnScope.(PaddingValues) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = topBar,
        containerColor = containerColor,
        contentWindowInsets = contentWindowInsets,
        modifier = modifier
    ) {
        CompositionLocalProvider(
            LocalContentColor provides LocalColorScheme.current.onBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            coroutineScope.launch {
                                keyboardController?.hide()
                                delay(250)
                                focusManager.clearFocus(true)
                            }
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content(it)
            }
        }
    }
}