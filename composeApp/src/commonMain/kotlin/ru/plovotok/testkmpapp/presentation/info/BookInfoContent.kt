package ru.plovotok.testkmpapp.presentation.info

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.plovotok.testkmpapp.presentation.description
import ru.plovotok.testkmpapp.presentation.isInternetError
import ru.plovotok.testkmpapp.presentation.ui.BaseScreen
import ru.plovotok.testkmpapp.presentation.ui.BookTopBar
import ru.plovotok.testkmpapp.presentation.ui.BookTopbarDefaults
import ru.plovotok.testkmpapp.presentation.ui.colorScheme
import ru.plovotok.testkmpapp.presentation.ui.components.BookInfoHeaderImage
import ru.plovotok.testkmpapp.presentation.ui.components.StarRating
import ru.plovotok.testkmpapp.presentation.ui.components.icons.BackButton
import ru.plovotok.testkmpapp.presentation.ui.components.screens.NoInternetScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookInfoContent(
    component: BookInfoComponent,
    showBackButton: Boolean,
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
                            .background(colorScheme.background)
                    )
                    val iconsColor = colorScheme.primary

                    BookTopBar(
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
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
                            if (showBackButton) {
                                BackButton(
                                    onClick = component::onBack,
                                    tint = iconsColor
                                )
                            }
                        },
                        actions = {
                            val isFavorite by remember {
                                derivedStateOf { model.isFavorite }
                            }
                            IconButton(
                                onClick = {
                                    if (isFavorite) {
                                        component.removeBookFromFavorites()
                                    } else {
                                        component.addBookToFavorites()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (!isFavorite) Icons.Default.BookmarkBorder else Icons.Default.Bookmark,
                                    contentDescription = "Add to favorite",
                                    tint = iconsColor
                                )
                            }
                        },
                        windowInsets = TopAppBarDefaults.windowInsets.only(WindowInsetsSides.Vertical + WindowInsetsSides.End),
                        colors = BookTopbarDefaults.colors().copy(containerColor = Color.Transparent)
                    )
                }
            },
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Vertical + WindowInsetsSides.End),
            modifier = modifier
        ) { paddings ->
            Box {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .run {
                            if (model.fullInfo != null) this.verticalScroll(scrollState) else this
                        }
                        .padding(bottom = paddings.calculateBottomPadding())
                        .padding(
                            start = paddings.calculateStartPadding(LocalLayoutDirection.current),
                            end = paddings.calculateEndPadding(LocalLayoutDirection.current)
                        ),
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
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = model.error.description()
                                            ?: "Something went wrong :(",
                                        modifier = Modifier.padding(horizontal = 40.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        } else {
                            model.fullInfo?.let {
                                Row(
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
                                            text = ((rating * 100).toInt()
                                                .toDouble() / 10).toString(),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            color = colorScheme.lightGrayTinted
                                        )
                                    }

                                    it.numberOfPages?.toInt()?.let {
                                        Text(
                                            text = "$it pages",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            color = colorScheme.lightGrayTinted
                                        )
                                    }

                                }
                                Spacer(modifier = Modifier.height(24.dp))
                                val description = it.desc?.ifEmpty { null } ?: "-"
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(SpanStyle(color = colorScheme.semiLightGrayTinted)) {
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
                                            withStyle(SpanStyle(color = colorScheme.semiLightGrayTinted)) {
                                                append("Authors: ")
                                            }
                                            it.authors.forEachIndexed { index, author ->
                                                append(author.name)
                                                if (index != it.authors.lastIndex) {
                                                    append(", ")
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(horizontal = 20.dp)
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

                Box(
                    modifier = Modifier
                        .background(colorScheme.background)
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd)
                        .padding(end = paddings.calculateEndPadding(LocalLayoutDirection.current))
                )
            }
        }
    }
}