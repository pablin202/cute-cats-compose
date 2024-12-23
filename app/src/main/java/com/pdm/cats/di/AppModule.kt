package com.pdm.cats.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pdm.cats.data.repository.PetsRepositoryImpl
import com.pdm.cats.data.networking.CatsApi
import com.pdm.cats.domain.repository.PetsRepository
import com.pdm.cats.presentation.petlist.PetListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModelOf
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
    viewModelOf(::PetListViewModel)

    // Retrofit
//    single {
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val original = chain.request()
//                val originalHttpUrl = original.url
//
//                val url = originalHttpUrl.newBuilder()
//                    .addQueryParameter("api_key", "live_NGETJZerQxYmeJdr1tGu8dfD4RpaB2HSH1CwSEpNi8LnmIfeVoqz5klPPOkLj0Ci")
//                    .build()
//
//                val request = original.newBuilder()
//                    .url(url)
//                    .build()
//
//                chain.proceed(request)
//            }
//            .build()
//
//        Retrofit.Builder()
//            .client(okHttpClient)
//            .addConverterFactory(
//                json.asConverterFactory(contentType = "application/json".toMediaType())
//            )
//            .baseUrl("https://api.thecatapi.com/v1/")
//            .build()
//    }

    single {
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl("https://cataas.com/api/")
            .build()
    }

    single { get<Retrofit>().create(CatsApi::class.java) }
}