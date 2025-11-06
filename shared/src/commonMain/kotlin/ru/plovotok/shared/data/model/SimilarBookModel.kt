package ru.plovotok.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.plovotok.shared.domain.models.BookPreview

@Serializable
data class SimilarBookModel(
    @SerialName("similar_books")
    val similarBooks: List<BookPreview> = emptyList()
)
