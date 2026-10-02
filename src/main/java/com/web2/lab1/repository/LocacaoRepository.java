package com.web2.lab1.repository;

import com.web2.lab1.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Integer> {
    List<Locacao> findByInquilinoNomeClienteContaining(String nome);
}