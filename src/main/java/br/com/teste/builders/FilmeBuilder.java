package br.com.teste.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.teste.entidades.Filme;

public class FilmeBuilder {
	
	private List<Filme> filmes = new ArrayList<>();
	
	private FilmeBuilder() {
	} 
	
	public static FilmeBuilder umaListaFilmesComOSeguinteTamanho(int tamanho) {
		FilmeBuilder builder = new FilmeBuilder();
		
		for(int i = 0; i<tamanho; i++) {
			Filme filme = new Filme();
			filme.setNome("Filme Teste " + Math.abs(new Random().nextInt()));
			filme.setEstoque(new Random().nextInt());
			filme.setPrecoLocacao(49.9d);
			builder.filmes.add(filme);
		}
		
		return builder;
	}
	
	public static FilmeBuilder umaListaFilmesComOSeguinteTamanhoSemEstoque(int tamanho) {
		FilmeBuilder builder = new FilmeBuilder();
		
		for(int i = 0; i<tamanho; i++) {
			Filme filme = new Filme();
			filme.setNome("Filme Teste " + Math.abs(new Random().nextInt()));
			filme.setEstoque(0);
			filme.setPrecoLocacao(49.9d);
			builder.filmes.add(filme);
		}
		
		return builder;
	}
	
	
	public List<Filme> agora() {
		return this.filmes;
	}
}
