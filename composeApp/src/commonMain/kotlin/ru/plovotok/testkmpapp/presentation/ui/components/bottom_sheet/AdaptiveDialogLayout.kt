package ru.plovotok.testkmpapp.presentation.ui.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue.Expanded
import androidx.compose.material3.SheetValue.PartiallyExpanded
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.window.core.layout.WindowSizeClass
import ru.plovotok.testkmpapp.presentation.ui.colorScheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveDialogLayout(
    onDismiss: () -> Unit,
    state: AdaptiveDialogState = rememberAdaptiveDialogState(),
    dialogPaddings: PaddingValues = PaddingValues(vertical = 16.dp),
    actions: (@Composable RowScope.() -> Unit)? = null,
    topBarColor: Color = colorScheme.sheetColor,
    extraTopContent: @Composable () -> Unit = {},
    content: @Composable ColumnScope.(PaddingValues) -> Unit,
) {
    val size = currentWindowAdaptiveInfo().windowSizeClass

    val isDialog = size.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) &&
            size.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)

    val scope = rememberCoroutineScope()

    val sheetState = state.sheetState

    val animateToDismiss: () -> Unit = {
        scope
            .launch { sheetState.hide() }
            .invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismiss()
                }
            }
    }

    if (isDialog) {
        DisposableEffect(Unit) {
            state.setVisibility(true)

            onDispose {
                state.setVisibility(false)
            }
        }
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(),
        ){
            val insets = WindowInsets.systemBars.asPaddingValues()
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorScheme.sheetColor)
                    .padding(dialogPaddings)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(topBarColor)
                ) {
                    actions?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            it()
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    extraTopContent()
                }
                content(insets)
            }
        }
    } else {

        DisposableEffect(Unit) {
            scope.launch {
                state.setVisibility(true)
                sheetState.show()
            }

            onDispose {
                state.setVisibility(false)
            }
        }

        ModalBottomSheet(
            onDismissRequest = onDismiss,
            contentWindowInsets = { WindowInsets(0.dp) },
            containerColor = colorScheme.sheetColor,
            dragHandle = null,
            sheetState = sheetState,
        ) {
            val insets = WindowInsets.systemBars.asPaddingValues()

            Column(
                modifier = Modifier.background(topBarColor)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    BottomSheetDefaults.DragHandle(
                        modifier = Modifier
                            .clickable {
                                when (state.sheetState.currentValue) {
                                    Expanded -> animateToDismiss()
                                    PartiallyExpanded -> scope.launch { sheetState.expand() }
                                    else -> scope.launch { sheetState.show() }
                                }
                            }
                    )
                    actions?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            it()
                        }
                    }
                }
                extraTopContent()
            }
            content(
                PaddingValues(
                    top = 0.dp,
                    bottom = insets.calculateBottomPadding(),
                    start = insets.calculateStartPadding(LocalLayoutDirection.current),
                    end = insets.calculateEndPadding(LocalLayoutDirection.current)
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberAdaptiveDialogState(): AdaptiveDialogState {
    val sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    return remember {
        AdaptiveDialogState(sheetState)
    }
}

@Stable
@OptIn(ExperimentalMaterial3Api::class)
class AdaptiveDialogState(
    internal val sheetState: SheetState
) {
    var isVisible: Boolean by mutableStateOf(false)
        private set

    fun setVisibility(visible: Boolean) {
        isVisible = visible
    }
    suspend fun dismiss() {
        sheetState.hide()
    }
}