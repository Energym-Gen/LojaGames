package com.generation.MinhaLojadeGames.repository;

	import java.util.Optional;


	import org.springframework.data.jpa.repository.JpaRepository;
	import com.generation.MinhaLojadeGames.Model.Usuario;

	public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByUsuario(String usuario);

}
