package ru.plovotok.testkmpapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import coil3.pathSegments
import coil3.toUri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.plovotok.testkmpapp.presentation.DeeplinkHelper.DeeplinkAction

object DeeplinkHelper {

    private val _link: MutableStateFlow<DeeplinkAction?> = MutableStateFlow(null)

    suspend fun consume(onAction: (DeeplinkAction) -> Unit): Nothing = _link.collect {
        if (it != null) {
            onAction(it)
            _link.emit(null)
        }
    }

    fun handleDeepLink(fullPath: String) {
        _link.update {
            parseLink(fullPath) ?: it
        }
    }

    // terminal: xcrun simctl openurl booted "compose://www.plovotok.ru/book/13469330"
    // adb shell am start -W -a android.intent.action.VIEW -d "compose://www.plovotok.ru/book/13469330" ru.plovotok.testkmpapp

    private fun parseLink(fullPath: String): DeeplinkAction? {
        val uri = fullPath.toUri()
        println("deeplink $uri")

        val segments = uri.pathSegments
        return when {
            //compose://www.plovotok.ru/book/13469330
            segments.contains("book") -> {
                try {
                    segments.lastOrNull()?.toIntOrNull()?.let {
                        DeeplinkAction.BookDetails(it)
                    }
                } catch (_: Throwable) {
                    null
                }
            }
            else -> null
        }
    }

    sealed class DeeplinkAction {
        data class BookDetails(val id: Int): DeeplinkAction()
    }
}

@Composable
fun DeeplinkHandler(onAction: (DeeplinkAction) -> Unit) {
    LaunchedEffect(Unit) {
        DeeplinkHelper.consume(onAction)
    }
}