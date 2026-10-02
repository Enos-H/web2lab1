package com.web2.lab1.service;

import com.web2.lab1.model.Locacao;
import com.web2.lab1.repository.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LocacaoService {

    private final LocacaoRepository repository;

    @Autowired
    public LocacaoService(LocacaoRepository locacaoRepository) {
        this.repository = locacaoRepository;
    }

    public List<Locacao> todas() {
        return repository.findAll();
    }

    public Optional<Locacao> buscaPor(Integer id) {
        return repository.findById(id);
    }

    public List<Locacao> buscaPor(String nomeInquilino) {
        return repository.findByInquilinoNomeClienteContaining(nomeInquilino);
    }

    @Transactional
    public Locacao salva(Locacao locacao) {
        return repository.save(locacao);
    }

    @Transactional
    public void removePelo(Integer id) {
        repository.deleteById(id);
    }

    public boolean naoExisteLocacaoCom(Integer id) {
        return !repository.existsById(id);
    }
}