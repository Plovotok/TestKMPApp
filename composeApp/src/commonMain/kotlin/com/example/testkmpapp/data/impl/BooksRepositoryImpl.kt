package com.example.testkmpapp.data.impl

import com.example.testkmpapp.data.ext.makeRequest
import com.example.testkmpapp.domain.models.BookPagingResponse
import com.example.testkmpapp.domain.BooksRepository
import com.example.testkmpapp.domain.Constants
import com.example.testkmpapp.domain.models.BookPreview
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

class BooksRepositoryImpl(
    private val client: HttpClient
): BooksRepository {


    override suspend fun getBooks(
        query: String,
        genres: List<String>,
        authors: List<String>,
        number: Int,
        offset: Int
    ): BookPagingResponse {
        val response: BookPagingResponse = client.makeRequest(
            path = "search-books",
            method = HttpMethod.Get,
            params = mapOf(
                "number" to number.toString(),
                "offset" to offset.toString(),
                "api-key" to Constants.BOOKS_API_KEY
            )
        )
        return response
    }

    override suspend fun getBookInfo(id: Int): BookPreview {
        return client.makeRequest(
            path = "$id",
            method = HttpMethod.Get,
            params = mapOf(
                "api-key" to Constants.BOOKS_API_KEY
            )
        )
    }


}