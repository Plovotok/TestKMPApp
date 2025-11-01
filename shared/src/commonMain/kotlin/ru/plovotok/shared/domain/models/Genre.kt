package ru.plovotok.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val requestName: String,
    val displayName: String
)