package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.springframework.dao.DataAccessException;

public class Principal {
	private Scanner teclado = new Scanner(System.in);
	private ConsumoAPI consumoAPI = new ConsumoAPI();
	private final String URL_BASE = "https://gutendex.com/books/";
	private ConvierteDatos convierteDatos = new ConvierteDatos();
	private LibroRepository libroRepository;
	private AutorRepository autorRepository;
	private List<Libro> libros;

	public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
		this.libroRepository = libroRepository;
		this.autorRepository = autorRepository;
	}

	public void consultarDatos(){
		var json = consumoAPI.obtenerDatos(URL_BASE);
		var libros = convierteDatos.obtenerDatos(json, Datos.class);
	}

	public void mostrarMenu(){
		var opcion = -1;
		while (opcion != 0) {
			var menu = """
                    1 - Buscar libro por título 
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en un determinado año
                    5 - Mostrar libros por idiomas
                    0 - Salir
                    """;
			System.out.println(menu);
			opcion = teclado.nextInt();
			teclado.nextLine();
			switch (opcion) {
				case 1:
					buscarLibroPorNombre();
					break;
				case 2:
					mostrarLibrosbuscados();
					break;
				case 3:
					mostrarAutores();
					break;
				case 4:
					mostrarAutorPorAnio();
					break;
				case 5:
					mostrarLibrosPorIdioma();
					break;
				case 0:
					System.out.println("Cerrando la aplicación...");
					break;
				default:
					System.out.println("Opción inválida");
			}
		}
	}
	public Optional<DatosLibros> getDatosLibro(){
		System.out.println("Ingrese el nombre del libro que desea buscar");
		String tituloLibro = teclado.next();
		teclado.nextLine();
		String json = consumoAPI.obtenerDatos(URL_BASE+"?search=" +tituloLibro.replace(" ","+"));
		Datos datosBuscados = convierteDatos.obtenerDatos(json,Datos.class);
		Optional<DatosLibros> libroBuscado = datosBuscados.Libros().stream()
				.filter(datosLibros -> datosLibros.titulo().toLowerCase().contains(tituloLibro.toLowerCase()))
				.findFirst();
		return libroBuscado;
	}

	public void buscarLibroPorNombre()  {
		Optional<DatosLibros> libroBuscado = getDatosLibro();
		if (libroBuscado.isPresent()){

			Libro libro = new Libro(libroBuscado.get());
			Autor autor = new Autor(libroBuscado.get().autor().get(0));

			Optional<Autor> autorBuscado = autorRepository.findByNombre(libroBuscado.get().autor().get(0).nombre());
			if (autorBuscado.isEmpty()){
				autorRepository.save(autor);
				libro.setAutor(autor);
				libroRepository.save(libro);
				System.out.println(libro.toString());
			} else {
				Optional<Libro> optionalLibro = libroRepository.findByTitulo(libroBuscado.get().titulo());
				if (optionalLibro.isPresent()){
					System.out.println("No se puede guardar el mismo libro más de una vez");
				}else {
					libro.setAutor(autorBuscado.get());
					libroRepository.save(libro);
					System.out.println(libro.toString());
				}
			}
		}
		else {
			System.out.println("Libro no encontrado");
		}

	}
	public void mostrarLibrosbuscados(){
		libros = libroRepository.findAll();
		libros.stream()
				.sorted(Comparator.comparing(Libro::getIdioma))
				.forEach(System.out::println);
	}

	public void mostrarAutores(){
		List<Autor> autores = autorRepository.findAll();
		autores.forEach(System.out::println);
	}

	public void mostrarAutorPorAnio(){
		System.out.println("Ingrese el año vivo de autor(es) que desea buscar");
		int anio = teclado.nextInt();
    teclado.nextLine();
		List<Autor> autors = autorRepository.autoresVivosPorAnio(anio);
		autors.forEach(System.out::println);
	}

	public void mostrarLibrosPorIdioma(){
		System.out.println("Ingrese el idioma para buscar los libros:" + "\n" +
				"es - español" + "\n"+
				"en - inglés" + "\n" +
				"fr - francés" + "\n"+
				"pt - portugués");
		String idioma = teclado.next();
		teclado.nextLine();
		Idioma idiomaElegido = Idioma.idiomaPrincipal(idioma);
		List<Libro> librosPorIdioma = libroRepository.findByIdioma(idiomaElegido);
		librosPorIdioma.forEach(System.out::println);
	}

}
