package ru.plovotok.testkmpapp.presentation.ui.components.panel

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanelsLayout
import com.arkivanov.decompose.router.panels.ChildPanelsMode

@OptIn(ExperimentalDecomposeApi::class)
class DynamicWidthChildPanelLayout(
    @param: FloatRange(0.0, 1.0) private val startWeight: Float = 0.5f,
    getLeftWidth: () -> Int
): ChildPanelsLayout {

    private val singleMeasurePolicy = SingleMeasurePolicy()
    private val dualMeasurePolicy = DualMeasurePolicy(startWeight = startWeight, leftWidth = getLeftWidth)

    @Composable
    override fun Layout(
        mode: ChildPanelsMode,
        main: @Composable () -> Unit,
        details: @Composable () -> Unit,
        extra: @Composable () -> Unit,
    ) {
        androidx.compose.ui.layout.Layout(
            content = {
                main()
                details()
                extra()
            },
            modifier = Modifier.fillMaxSize(),
            measurePolicy = when (mode) {
                ChildPanelsMode.SINGLE -> singleMeasurePolicy
                ChildPanelsMode.DUAL -> dualMeasurePolicy
                ChildPanelsMode.TRIPLE -> throw IllegalStateException("No impl for TRIPLE MODE")
            },
        )
    }

    private class SingleMeasurePolicy : MeasurePolicy {
        override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
            val placeables = measurables.map { it.measure(constraints) }

            return layout(constraints.maxWidth, constraints.maxHeight) {
                placeables.forEach {
                    it.placeRelative(x = 0, y = 0)
                }
            }
        }
    }

    private class DualMeasurePolicy(
        private val startWeight: Float = 0.5f,
        private val leftWidth: () -> Int
    ) : MeasurePolicy {

        override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
            val w1 = (constraints.maxWidth.toFloat() * startWeight).toInt() - leftWidth()
            val w2 = constraints.maxWidth - w1
            val placeable1 = measurables[0].measure(constraints.copy(maxWidth = w1, minWidth = w1))
            val placeable2 = measurables[1].measure(constraints.copy(maxWidth = w2, minWidth = w2))

            return layout(constraints.maxWidth, constraints.maxHeight) {
                placeable1.placeRelative(x = 0, y = 0)
                placeable2.placeRelative(x = w1, y = 0)
            }
        }
    }


}