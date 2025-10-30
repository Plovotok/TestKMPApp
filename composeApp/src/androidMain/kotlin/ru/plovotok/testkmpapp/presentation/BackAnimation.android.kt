package ru.plovotok.testkmpapp.presentation

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.materialPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackEvent

@OptIn(ExperimentalDecomposeApi::class)
actual fun getPredictiveBackAnimatable(initialBackEvent: BackEvent): PredictiveBackAnimatable = materialPredictiveBackAnimatable(initialBackEvent)
