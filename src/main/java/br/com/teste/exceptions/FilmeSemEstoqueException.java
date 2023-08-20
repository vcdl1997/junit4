package br.com.teste.exceptions;

public class FilmeSemEstoqueException extends Exception{

	private static final long serialVersionUID = 1L;

	public FilmeSemEstoqueException() {
		super("Filme sem Estoque");
	}
}
