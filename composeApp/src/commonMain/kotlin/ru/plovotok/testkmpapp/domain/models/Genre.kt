package ru.plovotok.testkmpapp.domain.models

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Genre(
    val requestName: String,
    val displayName: String
)