package com.example.testkmpapp.presentation.info

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.testkmpapp.presentation.description
import com.example.testkmpapp.presentation.isInternetError
import com.example.testkmpapp.presentation.ui.BaseScreen
import com.example.testkmpapp.presentation.ui.BookTopBar
import com.example.testkmpapp.presentation.ui.NoInternetScreen
import com.example.testkmpapp.presentation.ui.components.BookInfoHeaderImage
import com.example.testkmpapp.presentation.ui.components.StarRating
import com.example.testkmpapp.presentation.ui.components.icons.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookInfoContent(
    component: BookInfoComponent,
    modifier: Modifier = Modifier
) {
    val model by component.state.subscribeAsState()

    val scrollState = rememberScrollState()

    val density = LocalDensity.current

    val isDark = isSystemInDarkTheme()

    var isLightImage by remember {
        mutableStateOf(isDark)
    }

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
                    val iconsColor = if (showTitle) MaterialTheme.colorScheme.primary else {
                        if (isLightImage) {
                            MaterialTheme.colorScheme.onBackground
                        } else MaterialTheme.colorScheme.background
                    }

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
                            BackButton(
                                onClick = component::onBack,
                                tint = iconsColor
                            )
                        },
                        actions = {
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = Icons.Default.BookmarkBorder,
                                    contentDescription = "Add to favorite",
                                    tint = iconsColor
                                )
                            }
                        },
                        containerColor = Color.Transparent,
                    )
                }
            },
            modifier = modifier
        ) { paddings ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .run {
                        if (model.fullInfo != null) this.verticalScroll(scrollState) else this
                    }
                    .padding(bottom = paddings.calculateBottomPadding()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                BookInfoHeaderImage(
                    modifier = Modifier,
                    imageUrl = component.preview.image,
                    contentDescription = component.preview.title,
                    onLightChange = {
                        println("isLight = $it")
                        isLightImage = it
                    }
                )

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

                if (!model.isLoading) {
                    if (model.error != null) {
                        val isInternetError = model.error.isInternetError()

                        if (isInternetError) {
                            NoInternetScreen(
                                onRefresh = component::getBookInfo
                            )
                        } else {
                            Text(
                                text = model.error.description() ?: "Something went wrong :(",
                                modifier = Modifier.padding(horizontal = 40.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        model.fullInfo?.let {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.weight(1f, fill = false)
                                ) {
                                    val rating = it.rating?.average ?: 0.0
                                    StarRating(
                                        starCount = 1,
                                        rating = rating,
                                        modifier = Modifier.size(22.dp),
                                        rateColor = Color(0xffffca00),
                                        baseColor = Color.Gray
                                    )

                                    Text(
                                        text = ((rating * 100).toInt().toDouble() / 10).toString(),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Gray
                                    )
                                }

                                it.numberOfPages?.toInt()?.let {
                                    Text(
                                        text = "$it pages",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Gray
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.height(24.dp))
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
                            Spacer(Modifier.height(24.dp))

                            if (it.authors.isNotEmpty()) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(SpanStyle(color = Color.Gray)) {
                                            append("Authors: ")
                                        }
                                        it.authors.forEachIndexed { index, author ->
                                            append(author.name)
                                            if (index != it.authors.lastIndex) {
                                                append(", ")
                                            }
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                                )
                                Spacer(Modifier.height(24.dp))
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }


            }
        }
    }
}