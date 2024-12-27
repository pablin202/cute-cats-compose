package com.pdm.cats.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.pdm.cats.domain.models.CatModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data object PetListRoute

@Serializable
data class PetDetailsRoute(
    val catModel: CatModel
)

@Serializable
data object FavoritesRoute

val CatType = object : NavType<CatModel>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): CatModel? {
        return Json.decodeFromString(bundle.getString(key) ?: return null)
    }

    override fun parseValue(value: String): CatModel {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: CatModel): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: CatModel) {
        bundle.putString(key, Json.encodeToString(value))
    }
}
