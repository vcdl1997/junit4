package br.com.teste.servicos;


import static br.com.teste.builders.FilmeBuilder.umaListaFilmesComOSeguinteTamanho;
import static br.com.teste.builders.FilmeBuilder.umaListaFilmesComOSeguinteTamanhoSemEstoque;
import static br.com.teste.builders.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

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
import org.mockito.Mockito;

import br.com.teste.daos.LocacaoDAO;
import br.com.teste.daos.impl.LocacaoDaoImpl;
import br.com.teste.entidades.Filme;
import br.com.teste.entidades.Locacao;
import br.com.teste.entidades.Usuario;
import br.com.teste.exceptions.FilmeSemEstoqueException;
import br.com.teste.exceptions.ListaDeFilmesNaoInformadaException;
import br.com.teste.exceptions.UsuarioLocacaoNaoInformadoException;
import br.com.teste.exceptions.UsuarioNegativadoException;
import br.com.teste.utils.DataUtils;

//lembra de incluir dependencia eclemma no eclipse para calcular percentual de cobertura de testes no projeto
public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	private LocacaoService locacaoService;
	private Usuario usuario;
	private List<Filme> filmes = new ArrayList<Filme>();
	
	private LocacaoDAO dao = new LocacaoDaoImpl();
	private SPCService spcService;
	
	@Before
	public void setUp() {
		this.dao = Mockito.mock(LocacaoDAO.class);
		this.spcService = Mockito.mock(SPCService.class);
		this.locacaoService = new LocacaoService(dao, spcService);
		this.usuario = umUsuario().agora();
	}
	
	@Test
	public void testLocacao_comparandoSeDataLocacaoComDataAtualEObjetoNewDateSaoIguais(){
		try {
			//cenário
			this.filmes = umaListaFilmesComOSeguinteTamanho(2).agora();
			
			// ação			
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			//validação
			error.checkThat(
				DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), 
				is(true)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLocacao_comparandoSeDataRetornoLocacaoEDataAtualMaisUmDiaSaoIguais(){
		
		Assume.assumeTrue(!DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		try {
			//cenário
			this.filmes = umaListaFilmesComOSeguinteTamanho(2).agora();
			
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			error.checkThat(
				DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.adicionarDias(new Date(), 1)), 
				is(true)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUsuarioNegativadoNaoPodeFazerAlugarFilme(){
		try {
			//cenário
			this.filmes = umaListaFilmesComOSeguinteTamanho(2).agora();
			
			// ação			
			Mockito.when(spcService.possuiNegativacao(this.usuario)).thenReturn(true);
			
			this.locacaoService.alugarFilme(this.usuario, this.filmes);
		} catch (Exception e) {
			error.checkThat(e, is(instanceOf(UsuarioNegativadoException.class)));
		}
	}
	
	@Test
	public void testLocacao_filmeSemEstoque(){		
		try {
			//cenário
			this.filmes = umaListaFilmesComOSeguinteTamanhoSemEstoque(2).agora();
			
			// ação
			this.locacaoService.alugarFilme(this.usuario, this.filmes);
		} catch (Exception e) {
			// validação
			error.checkThat(e, is(instanceOf(FilmeSemEstoqueException.class)));
		}
	}
	
	@Test
	public void testLocacao_usuarioNaoInformado(){	
		//cenário
		this.filmes = umaListaFilmesComOSeguinteTamanho(2).agora();
		UsuarioLocacaoNaoInformadoException exception = new UsuarioLocacaoNaoInformadoException();
		
		try {
			// ação
			this.locacaoService.alugarFilme(null, this.filmes);
			
		} catch (Exception e) {
			// validação
			error.checkThat(e, is(instanceOf(UsuarioLocacaoNaoInformadoException.class)));
			error.checkThat(exception.getMessage(), is(equalTo(e.getMessage())));
		}
	}
	
	@Test
	public void testLocacao_filmeNaoInformado(){	
		//cenário
		this.filmes = umaListaFilmesComOSeguinteTamanho(2).agora();
		ListaDeFilmesNaoInformadaException exception = new ListaDeFilmesNaoInformadaException();
				
		try {
			// ação
			this.locacaoService.alugarFilme(this.usuario, null);
		} catch (Exception e) {
			// validação
			error.checkThat(e, is(CoreMatchers.instanceOf(ListaDeFilmesNaoInformadaException.class)));
			error.checkThat(exception.getMessage(), is(CoreMatchers.equalTo(e.getMessage())));
		}
	}
	
	@Test
	public void testLocacao_deveAplicarDescontoProgressivoAPartirDoTerceiroFilme(){	
		//cenário
		this.filmes = umaListaFilmesComOSeguinteTamanho(3).agora();
				
		try {
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			assertEquals(137.22, locacao.getValor(), 0.01);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLocacao_deveAplicarDescontoProgressivoDoTerceiroAteOQuartoFilme(){	
		//cenário
		this.filmes = umaListaFilmesComOSeguinteTamanho(4).agora();
				
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
		//cenário
		this.filmes = umaListaFilmesComOSeguinteTamanho(5).agora();
				
		try {
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			assertEquals(174.65, locacao.getValor(), 0.01);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLocacao_deveAplicarDescontoProgressivoDoTerceiroAteOSextoFilme(){	
		//cenário
		this.filmes = umaListaFilmesComOSeguinteTamanho(6).agora();
				
		try {
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			assertEquals(174.65, locacao.getValor(), 0.01);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLocacao_naoDeveConsiderarDomingoNaDataRetorno(){		
		
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenário
		this.filmes = umaListaFilmesComOSeguinteTamanho(2).agora();
		
		try {
			// ação
			Locacao locacao = this.locacaoService.alugarFilme(this.usuario, this.filmes);
			
			// validação
			error.checkThat(
				true, 
				is(equalTo(DataUtils.verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY)))
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}