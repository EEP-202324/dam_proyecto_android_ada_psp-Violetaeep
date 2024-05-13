package com.eep.aplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EstudianteService {
	
	    @Autowired
	    private EstudianteRepository estudianteRepository;

	    public List<Estudiante> getAllEstudiantes() {
	        return estudianteRepository.findAll();
	    }


	    public Optional<Estudiante> getEstudianteById(Long id) {
	        return estudianteRepository.findById(id);
	    }

	    public void deleteEstudiante(Long id) {
	        estudianteRepository.deleteById(id);
	    }

	    public Estudiante saveEstudiante(Estudiante estudiante) {
	        return estudianteRepository.save(estudiante);
	    }
	}
	


