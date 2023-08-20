package br.com.teste.exceptions;

public class ListaDeFilmesNaoInformadaException extends Exception{

	private static final long serialVersionUID = 1L;

	public ListaDeFilmesNaoInformadaException() {
		super("Para realizar uma locação é necessário que ao menos um filme seja informado");
	}
}
