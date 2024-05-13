package com.eep.dam.android.aplicacion_android.network.utils;

import com.eep.dam.android.aplicacion_android.network.Estudiante;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EstudianteService {

    @GET("listar/")
    Call<List<Estudiante>> getEstudiantes();
}