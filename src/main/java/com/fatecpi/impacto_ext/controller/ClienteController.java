package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.database.entity.ClienteEntity;
import com.fatecpi.impacto_ext.database.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Rota para CRIAR um novo cliente (Usada pelo seu ecrã "novo-cliente.tsx")
    @PostMapping
    public ResponseEntity<ClienteEntity> cadastrar(@RequestBody ClienteEntity cliente) {
        ClienteEntity salvo = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // Rota para LISTAR todos os clientes (Pode ser útil para os outros ecrãs do seu projeto)
    @GetMapping
    public ResponseEntity<List<ClienteEntity>> listarTodos() {
        List<ClienteEntity> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteEntity>> buscarCliente(@RequestParam String termo) {
        // Passa o termo tanto para a busca de nome quanto de CNPJ
        List<ClienteEntity> resultados = clienteRepository.findByNomeContainingIgnoreCaseOrCnpjContaining(termo, termo);
        
        // Retorna a lista (pode estar vazia, não tem problema)
        return ResponseEntity.ok(resultados);
    }
}