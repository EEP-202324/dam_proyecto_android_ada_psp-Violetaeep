package com.eep.dam.android.aplicacion_android.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "http://127.0.0.1:8080"
//        "http://10.0.2.2:8080" // esta es la IP del localhost del ordenador
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface EstudianteApiService {
    @GET("api_estudiantes")
    //@GET("cashcards")
    suspend fun getPhotos(): List<Estudiante>
}

object EstudianteApi {
    val retrofitService: EstudianteApiService by lazy {
        retrofit.create(EstudianteApiService::class.java)
    }
}