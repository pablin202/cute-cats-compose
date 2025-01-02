package com.pdm.cats.domain.use_cases

import android.util.Log
import com.pdm.cats.domain.models.countryNameToCodeMap

class GetCountryFlagUrlUseCase {
    operator fun invoke(countryName: String): String? {
        val countryCode = countryNameToCodeMap[countryName]

        Log.d("FLAG", "countryCode: $countryCode")
        return countryCode?.let { "https://flagsapi.com/$it/shiny/64.png" }
    }
}