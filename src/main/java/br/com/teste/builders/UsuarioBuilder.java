package br.com.teste.builders;

import br.com.teste.entidades.Usuario;

public class UsuarioBuilder {
	
	private Usuario usuario;
	
	private UsuarioBuilder() {
		
	}
	
	public static UsuarioBuilder umUsuario(){
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Teste");
		return builder;
	}

	public Usuario agora(){
		return this.usuario;
	}
}
