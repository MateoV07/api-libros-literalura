package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Datos;
import com.aluracursos.literalura.model.DatosLibros;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
	private Scanner teclado = new Scanner(System.in);
	private ConsumoAPI consumoAPI = new ConsumoAPI();
	private final String URL_BASE = "https://gutendex.com/books/";
	private ConvierteDatos convierteDatos = new ConvierteDatos();

	public void consultarDatos(){
		var json = consumoAPI.obtenerDatos(URL_BASE);
		var libros = convierteDatos.obtenerDatos(json, Datos.class);
	}
	public Optional<DatosLibros> getDatosLibro(){
		System.out.println("Ingrese el nombre del libro que desea buscar");
		String tituloLibro = teclado.nextLine();
		String json = consumoAPI.obtenerDatos(URL_BASE+"?search=" +tituloLibro.replace(" ","+"));
		Datos datosBuscados = convierteDatos.obtenerDatos(json,Datos.class);
		Optional<DatosLibros> libroBuscado = datosBuscados.Libros().stream()
				.filter(datosLibros -> datosLibros.titulo().toLowerCase().contains(tituloLibro.toLowerCase()))
				.findFirst();
		return libroBuscado;
	}

	public void buscarLibroPorNombre(){
		Optional<DatosLibros> libroBuscado = getDatosLibro();
		if (libroBuscado.isPresent()){
			System.out.println("----------- LIBRO -------------\n"+
					"Título: " +libroBuscado.get().titulo() + "\n" +
					"Autor: " + libroBuscado.get().autor().get(0).nombre() + "\n" +
					"Idioma: "+ libroBuscado.get().idiomas().get(0)+ "\n" +
					"Número de descargas: "+ libroBuscado.get().totalDescargas() + "\n" +
					"----------------------------------");
			Libro libro = new Libro(libroBuscado.get());
			System.out.println(libro);
		}
		else {
			System.out.println("Libro no encontrado");
		}

	}

}
