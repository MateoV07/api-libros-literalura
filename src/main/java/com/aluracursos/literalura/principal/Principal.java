package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Datos;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Scanner;

public class Principal {
	private Scanner teclado = new Scanner(System.in);
	private ConsumoAPI consumoAPI = new ConsumoAPI();
	private final String URL_BASE = "https://gutendex.com/books/";
	private ConvierteDatos convierteDatos = new ConvierteDatos();

	public void consultarDatos(){
		var json = consumoAPI.obtenerDatos(URL_BASE);
		var datos = convierteDatos.obtenerDatos(json, Datos.class);
		System.out.println(json);
		System.out.println(datos);
	}
}
