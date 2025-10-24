package com.example.testkmpapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BookPreview(
    val id: Int,
    val title: String,
    val subTitle: String? = null,
    val image: String? = null,
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