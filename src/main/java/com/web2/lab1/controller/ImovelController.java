package com.web2.lab1.controller;

import com.web2.lab1.model.Imovel;
import com.web2.lab1.service.ImovelService;
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
@RequestMapping("/imoveis")
@Tag(name = "Imoveis", description = "Gerenciamento de imóveis")
public class ImovelController {

    private final ImovelService service;

    @Autowired
    public ImovelController(ImovelService imovelService) {
        this.service = imovelService;
    }

    @GetMapping
    @Operation(summary = "Lista imóveis", description = "Retorna todos os imóveis ou filtra por tipo")
    public List<Imovel> lista(@RequestParam(required = false)
                              @Parameter(description = "Filtro por tipo do imóvel") String tipo) {
        if (tipo == null) {
            return service.todos();
        }
        return service.buscaPor(tipo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imovel> buscaPor(@PathVariable Integer id) {
        return service.buscaPor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Imovel> cadastro(@Valid @RequestBody Imovel imovel, UriComponentsBuilder builder) {

        final Imovel imovelSalvo = service.salva(imovel);

        final URI uri = builder
                .path("/imoveis/{id}")
                .buildAndExpand(imovelSalvo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(imovelSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imovel> atualiza(@Valid @RequestBody Imovel imovel, @PathVariable Integer id) {

        if (service.naoExisteImovelCom(id)) {
            return ResponseEntity.notFound().build();
        }

        imovel.setId(id);
        Imovel imovelAtualizado = service.salva(imovel);
        return ResponseEntity.ok(imovelAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Integer id) {

        Optional<Imovel> optional = service.buscaPor(id);

        if (optional.isPresent()) {
            service.removePelo(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}