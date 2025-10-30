package ru.plovotok.testkmpapp.presentation.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plovotok.testkmpapp.presentation.ui.colorScheme
import ru.plovotok.testkmpapp.presentation.ui.theme.BooksTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    BooksTheme {
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "OK",
            onClick = {}
        )
    }
}