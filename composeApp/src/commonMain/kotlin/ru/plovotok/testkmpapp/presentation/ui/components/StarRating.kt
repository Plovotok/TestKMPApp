package ru.plovotok.testkmpapp.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ru.plovotok.testkmpapp.presentation.ui.theme.BooksTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

private val minStarSize = 20.dp

object StarShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline.Generic {

        val path = Path().apply {
            moveTo(size.width * 0.5f, 0f)
            lineTo(size.width * 0.65f, size.height * 3f / 8f)
            lineTo(size.width, size.height * 3f / 8f)
            lineTo(size.width * 0.75f, size.height * 0.625f)
            lineTo(size.width * 0.8125f, size.height)
            lineTo(size.width * 0.5f, size.height * 0.8f)
            lineTo(size.width * 0.1875f, size.height)
            lineTo(size.width * 0.25f, size.height * 0.625f)
            lineTo(0f, size.height * 3f / 8f)
            lineTo(size.width * 0.35f, size.height * 3f / 8f)
            close()
        }

        return Outline.Generic(path = path)
    }
}

@Composable
fun StarRating(
    rating: Double,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSpacing: Dp = 8.dp,
    rateColor: Color = MaterialTheme.colorScheme.primary,
    baseColor: Color = MaterialTheme.colorScheme.surface,
) {
    StarRating(
        rating = rating.toFloat(),
        starCount = starCount,
        starSpacing = starSpacing,
        rateColor = rateColor,
        baseColor = baseColor,
        modifier = modifier,
    )
}


@Composable
fun StarRating(
    rating: Float,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSpacing: Dp = 8.dp,
    rateColor: Color = MaterialTheme.colorScheme.primary,
    baseColor: Color = MaterialTheme.colorScheme.surface,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    border: BorderStroke? = null,
) {
    val fillingStars = rating.toInt()
    val nail = rating - fillingStars

    Row(
        modifier = modifier
            .height(minStarSize),
        horizontalArrangement = Arrangement.spacedBy(starSpacing, Alignment.CenterHorizontally),
        verticalAlignment = verticalAlignment,
    ) {
        repeat(starCount) { index ->
            val brush = if (index < fillingStars) {
                Brush
                    .horizontalGradient(
                        colorStops = arrayOf(0f to rateColor, 1f to rateColor)
                    )
            } else if (index > fillingStars) {
                Brush
                    .horizontalGradient(
                        colorStops = arrayOf(0f to baseColor, 1f to baseColor)
                    )
            } else {
                Brush
                    .horizontalGradient(
                        colorStops = arrayOf(
                            0f to rateColor,
                            nail - 0.001f to rateColor,
                            nail to baseColor
                        )
                    )
            }

            Star(
                brush = brush,
                modifier = Modifier
                    .run {
                        if (border != null) {
                            this.border(border, StarShape)
                        } else this
                    }

            )
        }
    }
}

@Composable
private fun RowScope.Star(
    brush: Brush,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .defaultMinSize(minWidth = minStarSize, minHeight = minStarSize)
            .weight(1f, fill = false)
            .background(
                brush,
                StarShape
            )
    )
}


@Preview
@Composable
fun StarsPreview() {
    BooksTheme() {
        Column (
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StarRating(
                rating = 0f,
                starCount = 1,
                baseColor = Color.Transparent,
                modifier = Modifier,
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.primary
                )
            )
            StarRating(
                rating = 4.6f,
                starSpacing = 2.dp,
                modifier = Modifier,
            )
        }
    }
}