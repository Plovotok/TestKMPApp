package com.example.testkmpapp.presentation.ui.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.window.core.layout.WindowSizeClass
import com.example.testkmpapp.presentation.ui.colorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveDialogLayout(
    onDismiss: () -> Unit,
    actions: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.(PaddingValues) -> Unit,
) {
    val size = currentWindowAdaptiveInfo().windowSizeClass

    LaunchedEffect(size) {
        println("size = $size")
    }

    val isDialog = size.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) &&
            size.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)

    if (isDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(),
        ){
            val insets = WindowInsets.systemBars.asPaddingValues()
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorScheme.sheetColor)
                    .padding(vertical = 16.dp)
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
                content(insets)
            }
        }
    } else {
        val sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = onDismiss,
            contentWindowInsets = { WindowInsets(0.dp) },
            containerColor = colorScheme.sheetColor,
            dragHandle = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    BottomSheetDefaults.DragHandle()
                    actions?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            it()
                        }
                    }
                }
            },
            sheetState = sheetState,
        ) {
            val insets = WindowInsets.systemBars.asPaddingValues()
            content(insets)
        }
    }
}