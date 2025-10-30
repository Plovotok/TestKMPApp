package com.example.testkmpapp.presentation.ui.components.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testkmpapp.presentation.ui.theme.BooksTheme
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.ui.tooling.preview.Preview
import testkmpapp.composeapp.generated.resources.Res

@Composable
fun NoInternetScreen(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val composition by rememberLottieComposition {
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/connection_error.json").decodeToString()
            )
        }

        Image(
            painter = rememberLottiePainter(
                composition = composition,
                iterations = Compottie.IterateForever,
            ),
            contentDescription = "Lottie animation",
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = "The Internet connection\nappears to be offline",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        TextButton(
            onClick = onRefresh
        ) {
            Text(text = "Try again")
        }
    }
}

@Preview
@Composable
private fun NoInternetScreenPreview() {
    BooksTheme {
        NoInternetScreen(onRefresh = {})
    }
}