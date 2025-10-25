package com.example.testkmpapp.presentation.info

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.testkmpapp.domain.BooksRepository
import com.example.testkmpapp.presentation.base.BaseViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookInfoViewModel(
    private val id: Int
): BaseViewModel(), KoinComponent {

    private val repository: BooksRepository by inject()

    val state: MutableValue<BookInfoComponent.BookState> = MutableValue(BookInfoComponent.BookState())

    init {
        getBookInfo()
    }

    fun getBookInfo() {
        viewModelScope.launch {
            try {
                state.update { it.copy(isLoading = true) }
                val info = repository.getBookInfo(id)
                state.update { it.copy(fullInfo = info, isLoading = false) }
            } catch (e: Exception) {
                currentCoroutineContext().ensureActive()
                state.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

}