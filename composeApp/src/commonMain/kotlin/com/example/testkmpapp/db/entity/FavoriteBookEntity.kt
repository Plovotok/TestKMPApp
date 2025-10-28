package com.example.testkmpapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteBookEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val remoteId: Int,
    val title: String,
    val subtitle: String?,
    val imageUrl: String?,
)