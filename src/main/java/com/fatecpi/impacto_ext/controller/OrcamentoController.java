package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.models.ItemOrcamento;
import com.fatecpi.impacto_ext.models.Orcamento;
import com.fatecpi.impacto_ext.repositories.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/orcamentos") 
@CrossOrigin(origins = "*") 
public class OrcamentoController {

    @Autowired
    private OrcamentoRepository repository;

    @Autowired
    private com.fatecpi.impacto_ext.services.PdfService pdfService;

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
    public ResponseEntity<Page<Orcamento>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataEmissao").descending());
        Page<Orcamento> resultado = repository.findAll(pageable);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> baixarPdf(@PathVariable String id) {
        Orcamento orcamento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orçamento não localizado para o PDF."));
        
        byte[] pdf = pdfService.gerarPdfOrcamento(orcamento);

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "Orcamento_" + orcamento.getNomeCliente() + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdf);
    }

    @PatchMapping("/{id}/status-conclusao")
    public ResponseEntity<Orcamento> atualizarStatusConclusao(
            @PathVariable String id, 
            @RequestBody Map<String, Boolean> payload) {
        
        Orcamento orcamento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orçamento não encontrado."));
        
        boolean concluido = payload.get("concluido");
        orcamento.setConcluido(concluido);
        
        if (concluido) {
            orcamento.setDataConclusao(LocalDateTime.now());
        } else {
            orcamento.setDataConclusao(null);
        }
        
        Orcamento updated = repository.save(orcamento);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orcamento> atualizarOrcamentoCompleto(@PathVariable String id, @RequestBody Orcamento novosDados) {
        Orcamento orcamentoDoBanco = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orçamento inválido para edição."));
                
        orcamentoDoBanco.setNomeCliente(novosDados.getNomeCliente());
        orcamentoDoBanco.setCnpj(novosDados.getCnpj());
        orcamentoDoBanco.setEndereco(novosDados.getEndereco());
        orcamentoDoBanco.setTelefone(novosDados.getTelefone());
        orcamentoDoBanco.setObservacoes(novosDados.getObservacoes());
        orcamentoDoBanco.setValorTotal(novosDados.getValorTotal());
        orcamentoDoBanco.setItens(novosDados.getItens());
        orcamentoDoBanco.setUsuarioResponsavel(novosDados.getUsuarioResponsavel());
        
        Orcamento atualizado = repository.save(orcamentoDoBanco);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> buscarPorId(@PathVariable String id) {
        Orcamento orcamento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orçamento não encontrado."));
        return ResponseEntity.ok(orcamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOrcamento(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}