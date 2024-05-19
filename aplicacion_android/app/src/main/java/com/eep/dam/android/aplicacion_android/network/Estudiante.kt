package com.eep.dam.android.aplicacion_android.network

import kotlinx.serialization.Serializable
@Serializable
data class Estudiante(
    val idEstudiante: Int,
    var nombre: String,
    var apellido: String,
    var fechaNacimiento: String,
    var email: String,
    var curso: String?,
    var grado: String?
)

