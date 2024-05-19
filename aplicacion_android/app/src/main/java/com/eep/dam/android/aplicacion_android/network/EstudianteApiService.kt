package com.eep.dam.android.aplicacion_android.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASE_URL =
    "http://10.0.2.2:8080" // esta es la IP del localhost del ordenador
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface EstudianteApiService {
    @GET("api_estudiantes/")
    suspend fun getEstudiantes(): List<Estudiante>

    @GET("api_estudiantes/{id}")
    suspend fun getEstudianteById(@Path("id") id:Int): Estudiante

    @POST("api_estudiantes/create")
    suspend fun createEstudiante(@Body estudiante: Estudiante): Estudiante

    @DELETE("api_estudiantes/{id}")
    suspend fun deleteEstudiante(@Path("id") id:Int)

    @PUT("api_estudiantes/{id}")
    suspend fun updateEstudiante(@Path("id") id: Int, @Body estudiante: Estudiante)
}

object EstudianteApi {
    val retrofitService: EstudianteApiService by lazy {
        retrofit.create(EstudianteApiService::class.java)
    }
}