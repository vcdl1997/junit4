package br.com.teste.servicos;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.com.teste.exceptions.DivisaoPorZeroException;

public class CalculadoraTest {
	
	public ErrorCollector error = new ErrorCollector();
	
	private int valor1;
	private int valor2;
	private Calculadora calculadora;

	@Before
	public void setup() {
		this.calculadora = new Calculadora();
	}

	@Test
	public void deveSomarDoisValores() {
		//cenário
		this.valor1 = 5;
		this.valor2 = 5;
		Calculadora calculadora = new Calculadora();
		
		//ação
		int resultado = calculadora.somar(valor1, valor2);
		
		//verificação
		Assert.assertEquals(10, resultado);
		error.checkThat(10, CoreMatchers.is(resultado));
	}
	
	
	@Test
	public void deveSubtrairDoisValores() {
		//cenário
		this.valor1 = 5;
		this.valor2 = 5;
		
		//ação
		int resultado = this.calculadora.subtrair(valor1, valor2);
		
		//verificação
		error.checkThat(0, CoreMatchers.is(resultado));
	}
	
	@Test
	public void deveMultiplicarDoisValores() {
		//cenário
		this.valor1 = 5;
		this.valor2 = 5;
		
		//ação
		int resultado = calculadora.subtrair(valor1, valor2);
		
		//verificação
		error.checkThat(25, CoreMatchers.is(resultado));
	}
	
	@Test
	public void deveDividirDoisValores() {
		//cenário
		this.valor1 = 5;
		this.valor2 = 5;
		
		//ação
		int resultado = calculadora.subtrair(valor1, valor2);
		
		//verificação
		error.checkThat(0, CoreMatchers.is(resultado));
	}
	
	@Test
	public void deveLancarDivisaoPorZeroException() {
		//cenário
		this.valor1 = 5;
		this.valor2 = 0;
		
		//ação
		try {
			calculadora.dividir(valor1, valor2);
		} catch (Exception e) {			
			//verificação
			error.checkThat(e, CoreMatchers.instanceOf(DivisaoPorZeroException.class));
		}		
	}
}
