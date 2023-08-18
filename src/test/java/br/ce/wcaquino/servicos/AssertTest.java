package br.ce.wcaquino.servicos;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {

	@Test
	public void test(){
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		//Assert.assertEquals("Erro de Comparação", 1, 2); 
		Assert.assertEquals(1, 1); 
		Assert.assertEquals(0.51234, 0.512, 0.001); 
		Assert.assertEquals(Math.PI, 3.14, 0.01); 
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2); 
		Assert.assertEquals(i, i2.intValue()); 
		
		Assert.assertEquals("bola", "bola"); 
		Assert.assertTrue("bola".equalsIgnoreCase("Bola")); 
		Assert.assertTrue("bola".startsWith("b")); 
		
		
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = u2;
		Usuario u4 = null;
		
		Assert.assertEquals(u1, u2);
		Assert.assertSame(u2, u3); // se apontam para o mesmo objeto
		Assert.assertNotSame(u1, u3);
		Assert.assertNull(u4);
		Assert.assertNotNull(u1);
		
		Assert.assertThat(u1.getNome(), CoreMatchers.is("Usuario 1"));
		Assert.assertThat(u1.getNome(), CoreMatchers.is(CoreMatchers.not("Usuario 5")));
		Assert.assertThat(u1.getNome(), CoreMatchers.not(CoreMatchers.is("Usuario 2")));
		Assert.assertThat(u1.getNome(), CoreMatchers.not(CoreMatchers.is("Usuario 2")));
	}	
}
