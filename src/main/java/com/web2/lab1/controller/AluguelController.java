package com.web2.lab1.controller;

import com.web2.lab1.model.Aluguel;
import com.web2.lab1.service.AluguelService;
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
@RequestMapping("/alugueis")
@Tag(name = "Alugueis", description = "Gerenciamento de pagamentos mensais de locação")
public class AluguelController {

    private final AluguelService service;

    @Autowired
    public AluguelController(AluguelService aluguelService) {
        this.service = aluguelService;
    }

    @GetMapping
    @Operation(summary = "Lista aluguéis", description = "Retorna todos os aluguéis ou filtra por locação")
    public List<Aluguel> lista(@RequestParam(required = false)
                               @Parameter(description = "Filtro por id da locação") Integer idLocacao) {
        if (idLocacao == null) {
            return service.todos();
        }
        return service.buscaPorLocacao(idLocacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluguel> buscaPor(@PathVariable Integer id) {
        return service.buscaPor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Aluguel> cadastro(@Valid @RequestBody Aluguel aluguel, UriComponentsBuilder builder) {

        final Aluguel aluguelSalvo = service.salva(aluguel);

        final URI uri = builder
                .path("/alugueis/{id}")
                .buildAndExpand(aluguelSalvo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(aluguelSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluguel> atualiza(@Valid @RequestBody Aluguel aluguel, @PathVariable Integer id) {

        if (service.naoExisteAluguelCom(id)) {
            return ResponseEntity.notFound().build();
        }

        aluguel.setId(id);
        Aluguel aluguelAtualizado = service.salva(aluguel);
        return ResponseEntity.ok(aluguelAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Integer id) {

        Optional<Aluguel> optional = service.buscaPor(id);

        if (optional.isPresent()) {
            service.removePelo(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}