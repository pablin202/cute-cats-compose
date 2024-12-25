package com.pdm.cats.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat")
data class CatEntity(
    @PrimaryKey
    val id: String,
    val tags: String,
    @ColumnInfo(defaultValue = "0")
    val isFavorite: Boolean = false
)
