package br.com.teste.exceptions;

public class UsuarioNegativadoException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioNegativadoException() {
		super("Usuário negativado");
	}
}
