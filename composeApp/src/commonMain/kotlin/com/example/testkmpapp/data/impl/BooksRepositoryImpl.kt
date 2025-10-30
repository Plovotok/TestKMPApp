package com.example.testkmpapp.data.impl

import androidx.compose.ui.util.fastJoinToString
import com.example.testkmpapp.db.AppDatabase
import com.example.testkmpapp.db.entity.FavoriteBookEntity
import com.example.testkmpapp.data.ext.makeRequest
import com.example.testkmpapp.domain.models.BookPagingResponse
import com.example.testkmpapp.domain.BooksRepository
import com.example.testkmpapp.domain.Constants
import com.example.testkmpapp.domain.models.BookPreview
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BooksRepositoryImpl(
    private val client: HttpClient,
    favoritesDb: AppDatabase
) : BooksRepository {

    private val dao = favoritesDb.favoriteDao()

    override fun getFavorites(): Flow<List<BookPreview>> = dao.getFavorites().map {
        it.map {
            BookPreview(
                id = it.remoteId,
                title = it.title,
                subTitle = it.subtitle,
                image = it.imageUrl
            )
        }
    }

    override suspend fun addBookToFavorite(book: BookPreview) {
        val entity = FavoriteBookEntity(
            remoteId = book.id,
            title = book.title,
            subtitle = book.subTitle ?: "",
            imageUrl = book.image ?: ""
        )
        dao.addToFavorite(entity)
    }

    override suspend fun removeBookFromFavorite(book: BookPreview) {
        dao.removeFromFavorite(book.id)
    }


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
            params = buildMap {
                put("number", number.toString())
                put("offset", offset.toString())
                put("api-key", Constants.BOOKS_API_KEY)
                if (genres.isNotEmpty()) {
                    put("genres", genres.joinToString(","){ it } )
                }
            }
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