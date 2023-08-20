package br.com.teste.exceptions;

public class UsuarioLocacaoNaoInformadoException extends Exception{

	private static final long serialVersionUID = 1L;

	public UsuarioLocacaoNaoInformadoException() {
		super("Não é possível realizar uma locação sem que um usuário seja informado");
	}
}
