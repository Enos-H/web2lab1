package com.web2.lab1.controller;

import com.web2.lab1.model.Cliente;
import com.web2.lab1.service.ClienteService;
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
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes/inquilinos")
public class ClienteController {

    private final ClienteService service;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.service = clienteService;
    }

    @GetMapping
    @Operation(summary = "Lista clientes", description = "Retorna todos os clientes ou filtra por nome")
    public List<Cliente> lista(@RequestParam(required = false)
                               @Parameter(description = "Filtro por nome do cliente") String nome) {
        if (nome == null) {
            return service.todos();
        }
        return service.buscaPor(nome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscaPor(@PathVariable Integer id) {
        return service.buscaPor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastro(@Valid @RequestBody Cliente cliente, UriComponentsBuilder builder) {

        final Cliente clienteSalvo = service.salva(cliente);

        final URI uri = builder
                .path("/clientes/{id}")
                .buildAndExpand(clienteSalvo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(clienteSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualiza(@Valid @RequestBody Cliente cliente, @PathVariable Integer id) {

        if (service.naoExisteClienteCom(id)) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(id);
        Cliente clienteAtualizado = service.salva(cliente);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Integer id) {

        Optional<Cliente> optional = service.buscaPor(id);

        if (optional.isPresent()) {
            service.removePelo(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}