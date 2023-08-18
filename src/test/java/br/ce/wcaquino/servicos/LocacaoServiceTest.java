package br.ce.wcaquino.servicos;


import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService locacaoService;
	private Usuario usuario;
	private Filme filme;
	
	@Before
	public void setUp() {
		// cenário 
		this.locacaoService = new LocacaoService();
		this.usuario = new Usuario("Teste");
		this.filme = new Filme("Openheimer", 1, 40.90d);
	}

	@Test
	public void ComparandoSeDataLocacaoComDataAtualEObjetoNewDateSaoIguais(){
		// ação 
		Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filme);
		
		// validação
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
	}
	
	@Test
	public void ComparandoSeDataRetornoLocacaoEDataAtualMaisUmDiaSaoIguais(){
		// ação 
		Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filme);
		
		// validação
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.adicionarDias(new Date(), 1)));
	}
	
	

	
}