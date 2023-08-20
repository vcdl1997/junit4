package br.com.teste.exceptions;

public class LimiteDeFilmesPorLocacaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public LimiteDeFilmesPorLocacaoException() {
		super("A quantidade máxima de filmes que um usuário pode alugar ao mesmo tempo está limitada a 6 unidades.");
	}
}
