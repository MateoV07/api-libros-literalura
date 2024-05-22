package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(unique = true)
	private String titulo;
	@Enumerated(EnumType.STRING)
	private Idioma idioma;
	private Integer totalDescargas;
	@ManyToOne
	private Autor autor;

	public Libro(){}

	public Libro(DatosLibros datosLibros){
		this.titulo = datosLibros.titulo();
		this.idioma = Idioma.idiomaPrincipal(datosLibros.idiomas().get(0));
		this.totalDescargas = datosLibros.totalDescargas();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Integer getTotalDescargas() {
		return totalDescargas;
	}

	public void setTotalDescargas(Integer totalDescargas) {
		this.totalDescargas = totalDescargas;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	@Override
	public String toString() {

		return "----------- LIBRO -----------------" + "\n" +
				"Título: " + titulo + "\n" +
				"Autor: " + autor.getNombre() + "\n" +
				"Idioma: " + idioma + "\n" +
				"Número de descargas: " + totalDescargas + "\n"+
				"-----------------------------------";
	}

	public void printTitulos(){
		System.out.println(getTitulo());
	}
}
