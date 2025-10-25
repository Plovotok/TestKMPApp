package com.example.testkmpapp.domain

import com.example.testkmpapp.domain.models.BookPagingResponse
import com.example.testkmpapp.domain.models.BookPreview

interface BooksRepository {

    suspend fun getBooks(
        query: String = "",
        genres: List<String> = emptyList(),
        authors: List<String> = emptyList(),
        number: Int,
        offset: Int
    ): BookPagingResponse

    suspend fun getBookInfo(id: Int): BookPreview

}