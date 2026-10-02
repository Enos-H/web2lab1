package com.web2.lab1.service;

import com.web2.lab1.model.Imovel;
import com.web2.lab1.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImovelService {

    private final ImovelRepository repository;

    @Autowired
    public ImovelService(ImovelRepository imovelRepository) {
        this.repository = imovelRepository;
    }

    public List<Imovel> todos() {
        return repository.findAll();
    }

    public Optional<Imovel> buscaPor(Integer id) {
        return repository.findById(id);
    }

    public List<Imovel> buscaPor(String tipo) {
        return repository.findByTipoImovelContaining(tipo);
    }

    @Transactional
    public Imovel salva(Imovel imovel) {
        return repository.save(imovel);
    }

    @Transactional
    public void removePelo(Integer id) {
        repository.deleteById(id);
    }

    public boolean naoExisteImovelCom(Integer id) {
        return !repository.existsById(id);
    }
}