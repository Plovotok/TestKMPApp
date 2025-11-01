package ru.plovotok.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookPagingResponse(
    val books: List<List<BookPreview>>,
    val number: Int,
    val offset: Int,
    @SerialName("available")
    val total: Int
)
