package com.web2.lab1.repository;

import com.web2.lab1.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Integer> {
    List<Aluguel> findByLocacaoId(Integer idLocacao);
}