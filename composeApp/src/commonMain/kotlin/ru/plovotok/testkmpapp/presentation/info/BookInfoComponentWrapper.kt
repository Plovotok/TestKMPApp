package ru.plovotok.testkmpapp.presentation.info

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.testkmpapp.presentation.info.DefaultBookInfoComponentWrapper.ChildBook


interface BookInfoComponentWrapper: BackHandlerOwner {

    val preview: BookPreview

    val booksStack: Value<ChildStack<ChildBook, BookInfoComponent>>

    fun onBack()
}