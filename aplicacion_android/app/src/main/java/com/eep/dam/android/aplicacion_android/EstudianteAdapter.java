package com.eep.dam.android.aplicacion_android;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eep.dam.android.aplicacion_android.network.Estudiante;

import java.util.List;

public class EstudianteAdapter extends ArrayAdapter<Estudiante> {

    private Context context;

    private List<Estudiante> estudiantes;

    public EstudianteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public EstudianteAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public EstudianteAdapter(@NonNull Context context, int resource, @NonNull Estudiante[] objects) {
        super(context, resource, objects);
    }

    public EstudianteAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Estudiante[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public EstudianteAdapter(@NonNull Context context, int resource, @NonNull List<Estudiante> objects) {
        super(context, resource, objects);
    }

    public EstudianteAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Estudiante> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.estudiantes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        return super.getView(position, convertView, parent);
    }
}