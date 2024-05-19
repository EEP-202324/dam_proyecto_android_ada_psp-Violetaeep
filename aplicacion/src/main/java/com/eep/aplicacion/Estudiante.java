package com.eep.aplicacion;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Estudiante")
	private Long idEstudiante;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;

	@Column(name = "fecha_nacimiento")
	private String fechaNacimiento;

	@Column(name = "email")
	private String email;

	@Column(name = "curso")
	private String curso;

	@Column(name = "grado")
	private String grado;

	
	public Estudiante() {
	}

	public Long getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}
}
