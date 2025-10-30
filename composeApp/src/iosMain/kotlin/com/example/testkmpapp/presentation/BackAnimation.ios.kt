package com.example.testkmpapp.presentation

import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackEvent

@OptIn(ExperimentalDecomposeApi::class)
actual fun getPredictiveBackAnimatable(
    initialBackEvent: BackEvent
): PredictiveBackAnimatable = predictiveBackAnimatable(
    initialBackEvent = initialBackEvent,
    exitModifier = { progress, edge -> Modifier.slideExitModifier(progress = progress) },
    enterModifier = { progress, _ -> Modifier.slideEnterModifier(progress = progress) },
)
