package ru.plovotok.shared.domain

import kotlinx.coroutines.flow.Flow
import ru.plovotok.shared.domain.models.BookPagingResponse
import ru.plovotok.shared.domain.models.BookPreview
import ru.plovotok.shared.domain.models.Genre

interface BooksRepository {

    fun getFavorites(): Flow<List<BookPreview>>

    suspend fun addBookToFavorite(book: BookPreview)
    suspend fun removeBookFromFavorite(book: BookPreview)

    suspend fun getSimilarBooks(id: Int): List<BookPreview>

    suspend fun getBooks(
        query: String = "",
        genres: List<String> = emptyList(),
        authors: List<String> = emptyList(),
        number: Int,
        offset: Int
    ): BookPagingResponse

    suspend fun getBookInfo(id: Int): BookPreview

    fun getGenres(): List<Genre> = defaultGenres()

    companion object {
        private fun defaultGenres(): List<Genre> {
            return listOf(
                "action",
                "adventure",
                "anthropology",
                "astronomy",
                "archaeology",
                "architecture",
                "art",
                "aviation",
                "biography",
                "biology",
                "business",
                "chemistry",
                "children",
                "classics",
                "contemporary",
                "cookbook",
                "crafts",
                "crime",
                "dystopia",
                "economics",
                "education",
                "engineering",
                "environment",
                "erotica",
                "essay",
                "fairy_tales",
                "fantasy",
                "fashion",
                "feminism",
                "fiction",
                "finance",
                "folklore",
                "food",
                "gaming",
                "gardening",
                "geography",
                "geology",
                "graphic_novel",
                "health",
                "historical",
                "historical_fiction",
                "history",
                "horror",
                "how_to",
                "humor",
                "inspirational",
                "journalism",
                "law",
                "literary_fiction",
                "literature",
                "magical_realism",
                "manga",
                "martial_arts",
                "mathematics",
                "medicine",
                "medieval",
                "memoir",
                "mystery",
                "mythology",
                "nature",
                "nonfiction",
                "novel",
                "occult",
                "paranormal",
                "parenting",
                "philosophy",
                "physics",
                "picture_book",
                "poetry",
                "politics",
                "programming",
                "psychology",
                "reference",
                "relationships",
                "religion",
                "romance",
                "science_and_technology",
                "science_fiction",
                "self_help",
                "short_stories",
                "society",
                "sociology",
                "space",
                "spirituality",
                "sports",
                "text_book",
                "thriller",
                "travel",
                "true_crime",
                "war",
                "writing",
                "young_adult"
            ).map {
                Genre(
                    requestName = it,
                    displayName = it.replace("_", " ").capitalize()
                )
            }
        }
    }
}