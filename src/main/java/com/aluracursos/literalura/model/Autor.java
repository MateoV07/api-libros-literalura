package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")

public class Autor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column (unique = true)
	private String nombre;
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Libro> libros;
	private Integer fechaDeNacimiento;
	private Integer fechaDeFallecimiento;

	public Autor(){}

	public Autor (DatosAutor datosAutor){
		this.nombre = datosAutor.nombre();
		this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
		this.fechaDeFallecimiento = datosAutor.fechaDeFalleceminto();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		libros.forEach(libro -> libro.setAutor(this));
		this.libros = libros;
	}

	public Integer getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public Integer getFechaDeFallecimiento() {
		return fechaDeFallecimiento;
	}

	public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
		this.fechaDeFallecimiento = fechaDeFallecimiento;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	@Override
	public String toString() {
		return "Autor: "+ nombre + "\n" +
				"Libros: " + libros.stream().map(Libro::getTitulo).collect(Collectors.toList())+ "\n" +
				"FechaDeNacimiento: " + fechaDeNacimiento + "\n"+
				"FechaDeFallecimiento: " + fechaDeFallecimiento + "\n";
	}
}
