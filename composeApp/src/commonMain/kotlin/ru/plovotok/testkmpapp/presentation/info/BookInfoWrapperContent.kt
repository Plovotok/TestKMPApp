package ru.plovotok.testkmpapp.presentation.info

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.plovotok.testkmpapp.presentation.backAnimation
import ru.plovotok.testkmpapp.presentation.ui.BaseScreen

@Composable
fun BookInfoWrapperContent(
    component: BookInfoComponentWrapper,
    showBackButton: Boolean,
    modifier: Modifier = Modifier
) {
    val stack by component.booksStack.subscribeAsState()
    BaseScreen {
        Children(
            stack = stack,
            modifier = modifier.fillMaxSize(),
            animation = backAnimation(component.backHandler, component::onBack)
        ) {
            BookInfoContent(
                it.instance,
                showBackButton = it.configuration.hasParent || showBackButton,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}