package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Scanner;

public class Principal {
	private Scanner teclado = new Scanner(System.in);
	private ConsumoAPI consumoAPI = new ConsumoAPI();
	private final String URL_BASE = "https://gutendex.com/books/";
	private ConvierteDatos convierteDatos = new ConvierteDatos();
}
