package ru.plovotok.testkmpapp.presentation

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.materialPredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackEvent

@OptIn(markerClass = [ExperimentalDecomposeApi::class])
actual fun getPredictiveBackAnimatable(initialBackEvent: BackEvent): PredictiveBackAnimatable = materialPredictiveBackAnimatable(initialBackEvent)