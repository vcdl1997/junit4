package br.com.teste.daos;

import java.util.List;

import br.com.teste.entidades.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();
}
