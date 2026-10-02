package com.web2.lab1.controller;

import com.web2.lab1.model.Locacao;
import com.web2.lab1.service.LocacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locacoes")
@Tag(name = "Locacoes", description = "Gerenciamento de contratos de locação")
public class LocacaoController {

    private final LocacaoService service;

    @Autowired
    public LocacaoController(LocacaoService locacaoService) {
        this.service = locacaoService;
    }

    @GetMapping
    @Operation(summary = "Lista locações", description = "Retorna todas as locações ou filtra pelo nome do inquilino")
    public List<Locacao> lista(@RequestParam(required = false)
                               @Parameter(description = "Filtro por nome do inquilino") String nome) {
        if (nome == null) {
            return service.todas();
        }
        return service.buscaPor(nome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locacao> buscaPor(@PathVariable Integer id) {
        return service.buscaPor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Locacao> cadastro(@Valid @RequestBody Locacao locacao, UriComponentsBuilder builder) {

        final Locacao locacaoSalva = service.salva(locacao);

        final URI uri = builder
                .path("/locacoes/{id}")
                .buildAndExpand(locacaoSalva.getId())
                .toUri();

        return ResponseEntity.created(uri).body(locacaoSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Locacao> atualiza(@Valid @RequestBody Locacao locacao, @PathVariable Integer id) {

        if (service.naoExisteLocacaoCom(id)) {
            return ResponseEntity.notFound().build();
        }

        locacao.setId(id);
        Locacao locacaoAtualizada = service.salva(locacao);
        return ResponseEntity.ok(locacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Integer id) {

        Optional<Locacao> optional = service.buscaPor(id);

        if (optional.isPresent()) {
            service.removePelo(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}