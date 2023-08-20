package br.com.teste.servicos;

import static br.com.teste.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.teste.entidades.Filme;
import br.com.teste.entidades.Locacao;
import br.com.teste.entidades.Usuario;
import br.com.teste.exceptions.FilmeSemEstoqueException;
import br.com.teste.exceptions.LimiteDeFilmesPorLocacaoException;
import br.com.teste.exceptions.ListaDeFilmesNaoInformadaException;
import br.com.teste.exceptions.UsuarioLocacaoNaoInformadoException;
import br.com.teste.utils.DataUtils;

public class LocacaoService {
	
	public Double calcularValorLocacao(List<Filme> filmes) {
		double desconto = 0d, total = 0d;
		
		for(int i = 0; i < filmes.size(); i++) {
			double valorFilme = filmes.get(i).getPrecoLocacao();
			
			// Aplica descontos progressivos de 25% a partir do terceiro filme
			if(i >= 2) {
				desconto = i == 2 ? 0.25d : (desconto + 0.25d);
				valorFilme -= valorFilme * desconto;
			}
			
			total += valorFilme;
		}
		
		return total;
	}
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws Exception {
		
		if(usuario == null) throw new UsuarioLocacaoNaoInformadoException();
		
		if(filmes == null || filmes.isEmpty()) throw new ListaDeFilmesNaoInformadaException();
		
		boolean existeAlgumFilmeSemEstoque = filmes.stream().anyMatch(x -> x.getEstoque() == 0);
		
		if(existeAlgumFilmeSemEstoque) throw new FilmeSemEstoqueException();
		
		if(filmes.stream().count() > 6) throw new LimiteDeFilmesPorLocacaoException();
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(this.calcularValorLocacao(filmes));

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		
		locacao.setDataRetorno(dataEntrega);
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
	
	
}