package com.pdm.cats.data.local.converters

import androidx.room.TypeConverter

class CatTypeConverters {
    @TypeConverter
    fun fromTags(tags: List<String>): String {
        return tags.joinToString(",")
    }

    @TypeConverter
    fun toTags(tagsString: String): List<String> {
        return tagsString.split(",")
    }
}