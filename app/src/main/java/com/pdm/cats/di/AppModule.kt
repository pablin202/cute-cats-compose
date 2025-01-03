package com.pdm.cats.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pdm.cats.data.local.database.CatDatabase
import com.pdm.cats.data.networking.CatsApi
import com.pdm.cats.data.repository.PetsRepositoryImpl
import com.pdm.cats.domain.repository.PetsRepository
import com.pdm.cats.domain.use_cases.GetCountryFlagUrlUseCase
import com.pdm.cats.presentation.petdetails.PetDetailsViewModel
import com.pdm.cats.presentation.petlist.PetListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

val appModules = module {

    singleOf(::PetsRepositoryImpl).bind<PetsRepository>()
    single { Dispatchers.IO }
    singleOf(::GetCountryFlagUrlUseCase)
    singleOf(::PetListViewModel)
    singleOf(::PetDetailsViewModel)

    // Chucker
    single {
        val chuckerCollector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        val chuckerInterceptor = ChuckerInterceptor.Builder(androidContext())
            .collector(chuckerCollector)
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
        OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    // Retrofit
    single {

        val chuckerCollector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        val chuckerInterceptor = ChuckerInterceptor.Builder(androidContext())
            .collector(chuckerCollector)
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(
                        "api_key",
                        "live_NGETJZerQxYmeJdr1tGu8dfD4RpaB2HSH1CwSEpNi8LnmIfeVoqz5klPPOkLj0Ci"
                    )
                    .build()

                val request = original.newBuilder()
                    .url(url)
                    .build()

                chain.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl("https://api.thecatapi.com/v1/")
            .build()
    }

//    single {
//        Retrofit.Builder()
//            .addConverterFactory(
//                json.asConverterFactory(contentType = "application/json".toMediaType())
//            )
//            .baseUrl("https://cataas.com/api/")
//            .build()
//    }

    single { get<Retrofit>().create(CatsApi::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            CatDatabase::class.java,
            "cat-database"
        ).build()
    }
    single { get<CatDatabase>().catDao() }
    single { get<CatDatabase>().breedDao() }
}