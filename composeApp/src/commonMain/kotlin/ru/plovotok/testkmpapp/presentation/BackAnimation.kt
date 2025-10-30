package ru.plovotok.testkmpapp.presentation

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.stack.animation.isFront
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimator
import com.arkivanov.essenty.backhandler.BackEvent
import com.arkivanov.essenty.backhandler.BackHandler

@OptIn(ExperimentalDecomposeApi::class)
fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T> = predictiveBackAnimation(
    backHandler = backHandler,
    fallbackAnimation = stackAnimation(iosLikeSlide()),
    selector = { initialBackEvent, _, _ ->
        getPredictiveBackAnimatable(initialBackEvent)
    },
    onBack = onBack,
)

@OptIn(ExperimentalDecomposeApi::class)
expect fun getPredictiveBackAnimatable(initialBackEvent: BackEvent): PredictiveBackAnimatable

private fun iosLikeSlide(animationSpec: FiniteAnimationSpec<Float> = tween()): StackAnimator =
    stackAnimator(animationSpec = animationSpec) { factor, direction, content ->
        content(
            Modifier
                .then(if (direction.isFront) Modifier else Modifier.fade(factor + 1F))
                .offsetXFactor(factor = if (direction.isFront) factor else factor * 0.5F)
        )
    }

@OptIn(ExperimentalDecomposeApi::class)
fun iosLikeSlideExperimental(animationSpec: FiniteAnimationSpec<Float> = tween()): com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimator =
    com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimator(animationSpec = animationSpec) { factor, direction ->
        Modifier
            .then(if (direction.isFront) Modifier else Modifier.fade(factor + 1F))
            .offsetXFactor(factor = if (direction.isFront) factor else factor * 0.5F)
    }

fun Modifier.slideExitModifier(progress: Float): Modifier =
    offsetXFactor(progress)

fun Modifier.slideEnterModifier(progress: Float): Modifier =
    fade(progress).offsetXFactor((progress - 1f) * 0.5f)

private fun Modifier.fade(factor: Float) =
    drawWithContent {
        drawContent()
        drawRect(color = Color(red = 0F, green = 0F, blue = 0F, alpha = (1F - factor) / 4F))
    }

private fun Modifier.offsetXFactor(factor: Float): Modifier =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x = (placeable.width.toFloat() * factor).toInt(), y = 0)
        }
    }
