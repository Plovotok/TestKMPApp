package ru.plovotok.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookPreview(
    val id: Int,
    val title: String?,
    val subTitle: String? = null,
    val image: String? = null,
    @SerialName("published_date")
    val date: Double? = null,
    @SerialName("number_of_pages")
    val numberOfPages: Double? = null,
    @SerialName("description")
    val desc: String? = null,
    val authors: List<Author> = emptyList(),
    val rating: Rating? = null
)

@Serializable
data class Author(
    val id: Int,
    val name: String
)

@Serializable
data class Rating(
    val average: Double? = null
)