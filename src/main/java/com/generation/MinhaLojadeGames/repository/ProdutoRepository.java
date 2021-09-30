package com.generation.MinhaLojadeGames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.MinhaLojadeGames.Model.ProdutoModel;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long>{
	public List<ProdutoModel> findAllByDescricaoContainingIgnoreCase(String nome);
}


