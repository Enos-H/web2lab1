package com.web2.lab1.service;

import com.web2.lab1.model.Aluguel;
import com.web2.lab1.repository.AluguelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository repository;

    @Autowired
    public AluguelService(AluguelRepository aluguelRepository) {
        this.repository = aluguelRepository;
    }

    public List<Aluguel> todos() {
        return repository.findAll();
    }

    public Optional<Aluguel> buscaPor(Integer id) {
        return repository.findById(id);
    }

    public List<Aluguel> buscaPorLocacao(Integer idLocacao) {
        return repository.findByLocacaoId(idLocacao);
    }

    @Transactional
    public Aluguel salva(Aluguel aluguel) {
        return repository.save(aluguel);
    }

    @Transactional
    public void removePelo(Integer id) {
        repository.deleteById(id);
    }

    public boolean naoExisteAluguelCom(Integer id) {
        return !repository.existsById(id);
    }
}