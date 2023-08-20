package br.com.teste.servicos;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.com.teste.entidades.Filme;
import br.com.teste.entidades.Locacao;
import br.com.teste.entidades.Usuario;
import br.com.teste.exceptions.FilmeSemEstoqueException;
import br.com.teste.exceptions.ListaDeFilmesNaoInformadaException;
import br.com.teste.exceptions.UsuarioLocacaoNaoInformadoException;
import br.com.teste.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	private LocacaoService locacaoService;
	private Usuario usuario;
	private List<Filme> filmes = new ArrayList<Filme>();
	
	
	@Before
	public void setUp() {
		List<Filme> listaFilmes = new ArrayList<>();
		
		listaFilmes.add(new Filme("Oppenheimer", 3, 49.90d));
		listaFilmes.add(new Filme("Besouro Azul", 3, 49.90d));
		
		this.locacaoService = new LocacaoService();
		this.usuario = new Usuario("Teste");
		this.filmes = listaFilmes;
	}
	
	@Test
	public void testLocacao_comparandoSeDataLocacaoComDataAtualEObjetoNewDateSaoIguais(){
		try {
			// ação			
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			error.checkThat(
				DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), 
				CoreMatchers.is(true)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLocacao_comparandoSeDataRetornoLocacaoEDataAtualMaisUmDiaSaoIguais(){
		
		Assume.assumeTrue(!DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		try {
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			error.checkThat(
				DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.adicionarDias(new Date(), 1)), 
				CoreMatchers.is(true)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLocacao_filmeSemEstoque(){		
		try {
			// ação
			this.filmes.stream().forEach(x -> x.setEstoque(0));
			this.locacaoService.alugarFilme(this.usuario, this.filmes);
		} catch (Exception e) {
			// validação
			error.checkThat(e, CoreMatchers.is(CoreMatchers.instanceOf(FilmeSemEstoqueException.class)));
		}
	}
	
	@Test
	public void testLocacao_usuarioNaoInformado(){	
		// cenário
		UsuarioLocacaoNaoInformadoException exception = new UsuarioLocacaoNaoInformadoException();
		
		try {
			// ação
			this.locacaoService.alugarFilme(null, this.filmes);
			
		} catch (Exception e) {
			// validação
			error.checkThat(e, CoreMatchers.is(CoreMatchers.instanceOf(UsuarioLocacaoNaoInformadoException.class)));
			error.checkThat(exception.getMessage(), CoreMatchers.is(CoreMatchers.equalTo(e.getMessage())));
		}
	}
	
	@Test
	public void testLocacao_filmeNaoInformado(){	
		// cenário
		ListaDeFilmesNaoInformadaException exception = new ListaDeFilmesNaoInformadaException();
				
		try {
			// ação
			this.locacaoService.alugarFilme(this.usuario, null);
		} catch (Exception e) {
			// validação
			error.checkThat(e, CoreMatchers.is(CoreMatchers.instanceOf(ListaDeFilmesNaoInformadaException.class)));
			error.checkThat(exception.getMessage(), CoreMatchers.is(CoreMatchers.equalTo(e.getMessage())));
		}
	}
	
	@Test
	public void testLocacao_deveAplicarDescontoProgressivoAPartirDoTerceiroFilme(){	
		// cenário
		this.filmes.add(new Filme("Megatubarão 2", 3, 49.90d));
				
		try {
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			Assert.assertEquals(137.22, locacao.getValor(), 0.01);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLocacao_deveAplicarDescontoProgressivoDoTerceiroAteOQuartoFilme(){	
		// cenário
		this.filmes.add(new Filme("Megatubarão 2", 3, 49.90d));
		this.filmes.add(new Filme("Fale Comigo", 3, 49.90d));
				
		try {
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			Assert.assertEquals(162.18, locacao.getValor(), 0.01);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLocacao_deveAplicarDescontoProgressivoDoTerceiroAteOQuintoFilme(){	
		// cenário
		this.filmes.add(new Filme("Megatubarão 2", 3, 49.90d));
		this.filmes.add(new Filme("Fale Comigo", 3, 49.90d));
		this.filmes.add(new Filme("Indiana Jones e o Chamado do Destino", 3, 49.90d));
				
		try {
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			Assert.assertEquals(174.65, locacao.getValor(), 0.01);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLocacao_deveAplicarDescontoProgressivoDoTerceiroAteOSextoFilme(){	
		// cenário
		this.filmes.add(new Filme("Megatubarão 2", 3, 49.90d));
		this.filmes.add(new Filme("Fale Comigo", 3, 49.90d));
		this.filmes.add(new Filme("Indiana Jones e o Chamado do Destino", 3, 49.90d));
		this.filmes.add(new Filme("Ursinho Pooh: Sangue e Mel", 3, 49.90d));
				
		try {
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			Assert.assertEquals(174.65, locacao.getValor(), 0.01);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLocacao_naoDeveConsiderarDomingoNaDataRetorno(){		
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		try {
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			error.checkThat(
				true, 
				CoreMatchers.is(
					CoreMatchers.equalTo(DataUtils.verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY))
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}