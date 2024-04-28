package com.eep.dam.android.aplicacion_android.network

import kotlinx.serialization.Serializable
@Serializable
data class Estudiante(
    val idEstudiante: Int,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: String,
    val email: String,
    val curso: String?,
    val grado: String?
)

