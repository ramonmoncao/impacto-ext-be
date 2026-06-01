package com.fatecpi.impacto_ext.services;

import com.fatecpi.impacto_ext.models.ItemOrcamento;
import com.fatecpi.impacto_ext.models.Orcamento;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] gerarPdfOrcamento(Orcamento orcamento) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Cabeçalho da Empresa
            Font fonteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("IMPACTO EXTINTORES\nEQUIPAMENTOS DE COMBATE A INCÊNDIO", fonteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" \n")); // Espaço

            // Dados do Cliente e do Vendedor
            document.add(new Paragraph("CLIENTE: " + orcamento.getNomeCliente()));
            document.add(new Paragraph("CNPJ: " + (orcamento.getCnpj() != null ? orcamento.getCnpj() : "Não informado")));
            document.add(new Paragraph("ENDEREÇO: " + (orcamento.getEndereco() != null ? orcamento.getEndereco() : "Não informado")));
            document.add(new Paragraph("TELEFONE: " + (orcamento.getTelefone() != null ? orcamento.getTelefone() : "Não informado")));
            
            // ADICIONADO: Exibe o nome do vendedor responsável no corpo do PDF
            String vendedor = orcamento.getUsuarioResponsavel() != null ? orcamento.getUsuarioResponsavel() : "Não identificado";
            document.add(new Paragraph("VENDEDOR RESPONSÁVEL: " + vendedor));
            
            document.add(new Paragraph(" \n")); // Espaço

            // Tabela de Itens (O Carrinho)
            PdfPTable table = new PdfPTable(4); // 4 colunas
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("DESCRIÇÃO", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
            table.addCell(new PdfPCell(new Phrase("QTD.", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
            table.addCell(new PdfPCell(new Phrase("UNITÁRIO", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
            table.addCell(new PdfPCell(new Phrase("TOTAL", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));

            if (orcamento.getItens() != null) {
                for (ItemOrcamento item : orcamento.getItens()) {
                    table.addCell(item.getDescricaoProduto() != null ? item.getDescricaoProduto() : "---");
                    table.addCell(String.valueOf(item.getQuantidade()));
                    table.addCell(String.format("R$ %.2f", item.getValorUnitario()));
                    table.addCell(String.format("R$ %.2f", item.getSubtotal()));
                }
            }
            document.add(table);
            document.add(new Paragraph(" \n"));

            // Total e Rodapé
            Font fonteTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph total = new Paragraph(String.format("TOTAL GERAL: R$ %.2f", orcamento.getValorTotal()), fonteTotal);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.add(new Paragraph(" \nOBS: " + (orcamento.getObservacoes() != null ? orcamento.getObservacoes() : "")));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}