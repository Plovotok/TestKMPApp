package com.example.testkmpapp.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTopBar(
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {},
    showTitle: Boolean = true,
    title: @Composable () -> Unit,
    colors: TopAppBarColors = BookTopbarDefaults.colors(),
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            AnimatedVisibility(
                visible = showTitle,
                enter = slideInVertically { it } + fadeIn(),
                exit = ExitTransition.None
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    title()
                }
            }
        },
        modifier = modifier,
        actions = actions,
        windowInsets = windowInsets,
        navigationIcon = navigationIcon,
        colors = colors,
    )
}

object BookTopbarDefaults {

    @Composable
    fun colors(): TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        navigationIconContentColor = colorScheme.primary,
        actionIconContentColor = colorScheme.primary
    )
}
