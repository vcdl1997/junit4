package br.com.teste.servicos;

import br.com.teste.exceptions.DivisaoPorZeroException;

public class Calculadora {

	public int somar(int a, int b) {
		// TODO Auto-generated method stub
		return a + b;
	}

	public int subtrair(int a, int b) {
		// TODO Auto-generated method stub
		return a - b;
	}
	
	public int multiplicar(int a, int b) {
		// TODO Auto-generated method stub
		return a * b;
	}
	
	public int dividir(int a, int b) throws DivisaoPorZeroException {
		// TODO Auto-generated method stub
		if(b == 0) throw new DivisaoPorZeroException();
		
		return a / b;
	}

}
