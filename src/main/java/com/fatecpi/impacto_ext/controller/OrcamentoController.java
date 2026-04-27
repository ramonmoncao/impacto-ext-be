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
}