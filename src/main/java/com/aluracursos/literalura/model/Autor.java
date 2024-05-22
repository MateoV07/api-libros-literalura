package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;


public class Autor {
	private String nombre;
	private List<Libro> libros;
}
