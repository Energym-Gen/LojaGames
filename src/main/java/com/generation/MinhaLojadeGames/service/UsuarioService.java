package com.generation.MinhaLojadeGames.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.MinhaLojadeGames.Model.UserLogin;
import com.generation.MinhaLojadeGames.Model.Usuario;
import com.generation.MinhaLojadeGames.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	private static String encriptadorDeSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	
	public Optional<Object> cadastrarUsuario(Usuario usuario) {
		return repository.findByUsuario(usuario.getUsuario()).map(usuarioExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			usuario.setSenha(encriptadorDeSenha(usuario.getSenha()));
			return Optional.ofNullable(repository.save(usuario));
		});
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario){
		return repository.findById(usuario.getId()).map(usuarioExistente -> {
			usuarioExistente.setNome(usuario.getNome());
			usuarioExistente.setSenha(encriptadorDeSenha(usuario.getSenha()));
			return Optional.ofNullable(repository.save(usuarioExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}
	
	public Optional<UserLogin> logar (Optional<UserLogin> user){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodeAuth);
				
				user.get().setUsuarioID(usuario.get().getId());
				user.get().setToken(authHeader);
				user.get().setNome(usuario.get().getNome());
				user.get().setSenha(usuario.get().getSenha());
				
				return user;
			}
		}
		return null;
	}
}
