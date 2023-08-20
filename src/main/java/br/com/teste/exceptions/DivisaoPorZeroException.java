package br.com.teste.exceptions;

public class DivisaoPorZeroException extends Exception{

	private static final long serialVersionUID = 1L;

	public DivisaoPorZeroException() {
		super("Não é possível dividir por zero");
	}
}
