package com.eep.aplicacion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EstudianteController.class)
public class EstudianteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstudianteService estudianteService;

   
    @Test
    public void shouldReturnAllEstudiantes() throws Exception {
        Estudiante estudiante = new Estudiante(); // Assume a constructor or use setters to set up data
        estudiante.setNombre("Juan");

        given(estudianteService.getAllEstudiantes()).willReturn(Collections.singletonList(estudiante));

        mockMvc.perform(get("/api_estudiantes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andDo(print());
    }

    @Test
    public void shouldReturnEmptyListIfNoEstudiantesFound() throws Exception {
        given(estudianteService.getAllEstudiantes()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api_estudiantes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

   
    @Test
    public void shouldReturnEstudianteById() throws Exception {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Juan");

        given(estudianteService.getEstudianteById(1L)).willReturn(Optional.of(estudiante));

        mockMvc.perform(get("/api_estudiantes/1")
                .contentType(MediaType.APPLICATION_JSON))  
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test                   
    public void shouldReturnNotFoundIfEstudianteDoesNotExist() throws Exception {
        given(estudianteService.getEstudianteById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api_estudiantes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

  
    @Test
    public void shouldCreateEstudiante() throws Exception {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Juan");

        given(estudianteService.saveEstudiante(any(Estudiante.class))).willReturn(estudiante);

        mockMvc.perform(post("/api_estudiantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Juan\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

   

  
    @Test
    public void shouldUpdateEstudiante() throws Exception {
        Estudiante existingEstudiante = new Estudiante();
        existingEstudiante.setNombre("Juan");
        Estudiante updatedEstudiante = new Estudiante();
        updatedEstudiante.setNombre("Juan Updated");

        given(estudianteService.getEstudianteById(1L)).willReturn(Optional.of(existingEstudiante));
        given(estudianteService.saveEstudiante(any(Estudiante.class))).willReturn(updatedEstudiante);

        mockMvc.perform(put("/api_estudiantes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Juan Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Updated"));
    }

    @Test            
    public void shouldReturnNotFoundWhenUpdatingNonExistentEstudiante() throws Exception {
        given(estudianteService.getEstudianteById(1L)).willReturn(Optional.empty());

        mockMvc.perform(put("/api_estudiantes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Juan\"}"))
                .andExpect(status().isNotFound());
    }

    
    @Test
    public void shouldDeleteEstudiante() throws Exception {
        given(estudianteService.getEstudianteById(1L)).willReturn(Optional.of(new Estudiante()));

        mockMvc.perform(delete("/api_estudiantes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNotFoundWhenDeletingNonExistentEstudiante() throws Exception {
        given(estudianteService.getEstudianteById(1L)).willReturn(Optional.empty());

        mockMvc.perform(delete("/api_estudiantes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
