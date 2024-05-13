package com.eep.dam.android.aplicacion_android.network.utils;
public class Apis {

    public static final String URL_001="http://127.0.0.1:8080/api_estudiante/";
    public static EstudianteService getEstudianteService(){
        return Cliente.getCliente(URL_001).create(EstudianteService.class);
    }

}
