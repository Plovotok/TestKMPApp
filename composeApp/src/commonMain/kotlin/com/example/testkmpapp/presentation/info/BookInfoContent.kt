package com.example.testkmpapp.presentation.info

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.testkmpapp.presentation.ui.BaseScreen
import com.example.testkmpapp.presentation.ui.BookTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookInfoContent(
    component: BookInfoComponent,
    modifier: Modifier = Modifier
) {
    val model by component.state.subscribeAsState()

    val scrollState = rememberScrollState()

    val density = LocalDensity.current

    val effect = LocalOverscrollFactory.current?.createOverscrollEffect()

    BoxWithConstraints {

        var showTitle by remember {
            mutableStateOf(false)
        }

        val topBarAlpha by animateFloatAsState(
            if (showTitle) 1f else 0f
        )

        BaseScreen(
            topBar = {
                Box {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .graphicsLayer {
                                this.alpha = topBarAlpha
                            }
                            .background(MaterialTheme.colorScheme.background)
                    )
                    BookTopBar(
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                AsyncImage(
                                    model = component.preview.image,
                                    contentDescription = component.preview.title,
                                    modifier = Modifier
                                        .size(40.dp),
                                    contentScale = ContentScale.Fit
                                )

                                Text(
                                    text = component.preview.title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                )
                            }
                        },
                        showTitle = showTitle,
                        navigationIcon = {
                            TextButton(onClick = component::onBack) {
                                Text(text = "Back")
                            }
                        },
                        containerColor = Color.Transparent,
                    )
                }
            },
            modifier = modifier
        ) { paddings ->

            var scale by remember {
                mutableFloatStateOf(1f)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .overscroll(effect)
                    .padding(bottom = paddings.calculateBottomPadding()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .onGloballyPositioned {
                            with(density) {
                                scale = 1f + (it.positionInWindow().y / 200.dp.toPx())
                            }
                        }
                ) {
                    AsyncImage(
                        model = component.preview.image,
                        contentDescription = component.preview.title,
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = (scale + 0.1f).coerceAtLeast(1.1f)
                                scaleY = (scale + 0.1f).coerceAtLeast(1.1f)
                                this.transformOrigin = TransformOrigin(0.5f, 1f)
                            }
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                            .blur(radius = 20.dp),
                        contentScale = ContentScale.Crop
                    )

                    AsyncImage(
                        model = component.preview.image,
                        contentDescription = component.preview.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale.coerceAtLeast(1f)
                                scaleY = scale.coerceAtLeast(1f)
                                this.transformOrigin = TransformOrigin(0.5f, 1f)
                            }
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = component.preview.title,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            with(density) {
                                showTitle =
                                    it.positionInWindow().y + it.size.height <= paddings.calculateTopPadding()
                                        .toPx()
                            }
                        },
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(24.dp))

                model.fullInfo?.let {
                    val description = it.desc?.ifEmpty { null } ?: "-"
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.Gray)) {
                                append("Description: ")
                            }
                            append(description)
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                    )
                }


            }
        }
    }
}