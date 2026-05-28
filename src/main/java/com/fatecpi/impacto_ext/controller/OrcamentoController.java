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
    // Injete o serviço logo abaixo do repository
    @Autowired
    private com.fatecpi.impacto_ext.services.PdfService pdfService;

    // Nova Rota para baixar o PDF
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> baixarPdf(@PathVariable String id) {
        Orcamento orcamento = repository.findById(id).orElseThrow();
        byte[] pdf = pdfService.gerarPdfOrcamento(orcamento);

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "Orcamento_" + orcamento.getNomeCliente() + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdf);
    }

    @PatchMapping("/{id}/status-conclusao")
    
        public ResponseEntity<Orcamento> atualizarStatusConclusao(
            @PathVariable String id, 
            @RequestBody java.util.Map<String, Boolean> payload) {
    
            Orcamento orcamento = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
    
            boolean concluido = payload.get("concluido");
            orcamento.setConcluido(concluido);
    
            Orcamento atualizado = repository.save(orcamento);
            return ResponseEntity.ok(atualizado);
}

        @DeleteMapping("/{id}")
            public ResponseEntity<Void> excluirOrcamento(@PathVariable String id) {
            // Verifica se o registro realmente existe antes de tentar apagar
            if (!repository.existsById(id)) {
        return ResponseEntity.notFound().build();
            }
    
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna o status HTTP 244 (Sem Conteúdo), padrão de exclusão com sucesso
}

    @PutMapping("/{id}")
        public ResponseEntity<Orcamento> atualizarOrcamentoCompleto(@PathVariable String id, @RequestBody Orcamento novosDados) {
    Orcamento orcamentoDoBanco = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
            
    // Atualiza os dados comerciais e operacionais vindos do front
    orcamentoDoBanco.setNomeCliente(novosDados.getNomeCliente());
    orcamentoDoBanco.setEndereco(novosDados.getEndereco());
    orcamentoDoBanco.setTelefone(novosDados.getTelefone());
    orcamentoDoBanco.setObservacoes(novosDados.getObservacoes());
    orcamentoDoBanco.setValorTotal(novosDados.getValorTotal());
    orcamentoDoBanco.setItens(novosDados.getItens());
    
    // Mantém a data de emissão original, mas atualiza os dados
    Orcamento atualizado = repository.save(orcamentoDoBanco);
    return ResponseEntity.ok(atualizado);
}

    @GetMapping("/{id}")
        public ResponseEntity<Orcamento> buscarPorId(@PathVariable String id) {
    Orcamento orcamento = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
    return ResponseEntity.ok(orcamento);
}

}