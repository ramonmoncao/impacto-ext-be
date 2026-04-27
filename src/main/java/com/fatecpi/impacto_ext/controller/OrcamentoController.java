package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.models.ItemOrcamento;
import com.fatecpi.impacto_ext.models.Orcamento;
import com.fatecpi.impacto_ext.repositories.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orcamentos") 
@CrossOrigin(origins = "*") 
public class OrcamentoController {

    @Autowired
    private OrcamentoRepository repository;

    
    @PostMapping
    public ResponseEntity<Orcamento> criarOrcamento(@RequestBody Orcamento orcamento) {
        orcamento.setDataEmissao(LocalDateTime.now());
        double total = 0.0;
        if (orcamento.getItens() != null) {
            for (ItemOrcamento item : orcamento.getItens()) {
                total += item.getSubtotal();
            }
        }
        orcamento.setValorTotal(total);

        Orcamento salvo = repository.save(orcamento);
        
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Orcamento>> listarTodos() {
        List<Orcamento> lista = repository.findAll();
        return ResponseEntity.ok(lista);
    }
}