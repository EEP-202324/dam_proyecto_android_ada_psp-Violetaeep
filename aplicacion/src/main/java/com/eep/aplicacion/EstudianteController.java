package com.eep.aplicacion;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api_estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;


    @GetMapping("/")
    public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.getAllEstudiantes();
        return new ResponseEntity<>(estudiantes, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        Optional<Estudiante> estudiante = estudianteService.getEstudianteById(id);
        if(estudiante.isPresent()) {
            return new ResponseEntity<>(estudiante.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Estudiante> createEstudiante(@RequestBody Estudiante estudiante) {
        Estudiante savedEstudiante = estudianteService.saveEstudiante(estudiante);
        return new ResponseEntity<>(savedEstudiante, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteDetails) {
        Optional<Estudiante> estudiante = estudianteService.getEstudianteById(id);
        if(estudiante.isPresent()) {   //realmente siempre vamos a eleguir un estudiante que exista
            Estudiante updatedEstudiante = estudiante.get();
            updatedEstudiante.setNombre(estudianteDetails.getNombre());
            updatedEstudiante.setApellido(estudianteDetails.getApellido());
            updatedEstudiante.setFechaNacimiento(estudianteDetails.getFechaNacimiento());
            updatedEstudiante.setEmail(estudianteDetails.getEmail());
            updatedEstudiante.setCurso(estudianteDetails.getCurso());
            updatedEstudiante.setGrado(estudianteDetails.getGrado());
            estudianteService.saveEstudiante(updatedEstudiante);
            return new ResponseEntity<>(updatedEstudiante, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
        Optional<Estudiante> estudiante = estudianteService.getEstudianteById(id);
        if(estudiante.isPresent()) {
            estudianteService.deleteEstudiante(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
      }
}

