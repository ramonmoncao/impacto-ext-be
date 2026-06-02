package com.fatecpi.impacto_ext.services;

import com.fatecpi.impacto_ext.models.Orcamento; // Importe o seu modelo real
import com.fatecpi.impacto_ext.models.ItemOrcamento; // Importe o seu modelo real
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] gerarPdfOrcamento(Orcamento orcamento) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // Define margens finas para caber bastante conteúdo
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Configuração das Fontes
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font fontNegrito = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.RED);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font fontPequena = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, Font.UNDERLINE);
            Font fontRodape = FontFactory.getFont(FontFactory.HELVETICA, 9);
            Font fontSub = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.UNDERLINE);

            // ==========================================
            // 1. CABEÇALHO (LOGO + DADOS DA EMPRESA)
            // ==========================================
            PdfPTable tableCabecalho = new PdfPTable(2);
            tableCabecalho.setWidthPercentage(100);
            tableCabecalho.setWidths(new float[]{2f, 2f}); // Logo ocupa menos espaço

            // Célula da Logo
            PdfPCell cellLogo = new PdfPCell();
            cellLogo.setBorder(Rectangle.NO_BORDER);
            try {
                // Puxa a logo da pasta resources/images
                Image logo = Image.getInstance(new ClassPathResource("img/logo.png").getURL());
                logo.scaleToFit(200, 200);
                cellLogo.addElement(logo);
            } catch (Exception e) {
                // Fallback caso a imagem não seja encontrada
                cellLogo.addElement(new Paragraph("IMPACTO EXTINTORES", fontTitulo));
            }
            tableCabecalho.addCell(cellLogo);

            // Célula dos Dados da Empresa
            PdfPCell cellEmpresa = new PdfPCell();
            cellEmpresa.setBorder(Rectangle.NO_BORDER);
            cellEmpresa.addElement(new Paragraph("IMPACTO EXTINTORES LTDA-ME", fontTitulo));
            cellEmpresa.addElement(new Paragraph("Manutenção e Comércio de Materiais c/ Incêndio Ltda", fontNormal));
            cellEmpresa.addElement(new Paragraph("Rua: Pau Brasil, 224 Bairro Figueira - Monte Mor - SP", fontNormal));
            cellEmpresa.addElement(new Paragraph("Fones: (19) 99452-5148 / (19) 99388-5151", fontNormal));
            cellEmpresa.addElement(new Paragraph("E-mail: extintoresimpacto@gmail.com", fontNormal));
            cellEmpresa.addElement(new Paragraph("CNPJ: 07.038.566/0001-51  I.E.: 465.068.852.09", fontNegrito));
            
            tableCabecalho.addCell(cellEmpresa);
            document.add(tableCabecalho);
            document.add(new Paragraph(" ")); // Espaçamento

            // ==========================================
            // 2. DADOS DO CLIENTE (Grade com Bordas)
            // ==========================================
            PdfPTable tableCliente = new PdfPTable(2);
            tableCliente.setWidthPercentage(100);
            tableCliente.setWidths(new float[]{3f, 1f}); // Coluna principal maior
            
            // Formatador de Data atual
            String dataAtual = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            adicionarCelulaClienteMista(tableCliente, "CLIENTE: ", fontNormal, orcamento.getNomeCliente(), fontSub);
            adicionarCelulaCliente(tableCliente, "DATA: " + dataAtual, fontNormal);
            
            adicionarCelulaCliente(tableCliente, "ENDEREÇO: " + orcamento.getEndereco(), fontNormal);
            adicionarCelulaCliente(tableCliente, "Nº: ", fontNormal); // Se tiver número separado no modelo

            adicionarCelulaCliente(tableCliente, "BAIRRO: ", fontNormal); // Preencher se houver no DB
            adicionarCelulaCliente(tableCliente, "CIDADE: ", fontNormal);

            adicionarCelulaCliente(tableCliente, "CNPJ: " + orcamento.getCnpj(), fontNormal);
            adicionarCelulaCliente(tableCliente, "TELEFONE: " + orcamento.getTelefone(), fontNormal);

            document.add(tableCliente);
            
            // Texto de Introdução
            Paragraph intro = new Paragraph("\nTemos a satisfação de submeter à apreciação de V.Sas, nossa proposta de fornecimento de recarga em extintores contra incêndio conforme segue:\n\n", fontNormal);
            document.add(intro);

            // ==========================================
            // 3. TABELA DE PRODUTOS
            // ==========================================
            PdfPTable tableItens = new PdfPTable(4);
            tableItens.setWidthPercentage(100);
            tableItens.setWidths(new float[]{4.5f, 1f, 1.5f, 1.5f}); // Proporções das colunas

            // Cabeçalho da Tabela
            adicionarCelulaTabela(tableItens, "DESCRIÇÃO", fontSub, Color.LIGHT_GRAY, PdfPCell.ALIGN_LEFT);
            adicionarCelulaTabela(tableItens, "QTD.", fontSub, Color.LIGHT_GRAY, PdfPCell.ALIGN_CENTER);
            adicionarCelulaTabela(tableItens, "UNITÁRIO R$", fontSub, Color.LIGHT_GRAY, PdfPCell.ALIGN_CENTER);
            adicionarCelulaTabela(tableItens, "TOTAL R$", fontSub, Color.LIGHT_GRAY, PdfPCell.ALIGN_CENTER);

            // Loop dos Itens do Carrinho
            for (ItemOrcamento item : orcamento.getItens()) {
                adicionarCelulaTabela(tableItens, item.getDescricaoProduto(), fontNormal, Color.WHITE, PdfPCell.ALIGN_LEFT);
                adicionarCelulaTabela(tableItens, String.valueOf(item.getQuantidade()), fontNormal, Color.WHITE, PdfPCell.ALIGN_CENTER);
                adicionarCelulaTabela(tableItens, String.format("%.2f", item.getValorUnitario()), fontNormal, Color.WHITE, PdfPCell.ALIGN_CENTER);
                
                double subtotal = item.getQuantidade() * item.getValorUnitario();
                adicionarCelulaTabela(tableItens, String.format("%.2f", subtotal), fontNormal, Color.WHITE, PdfPCell.ALIGN_CENTER);
            }

            // Linha de Total Geral
            PdfPCell cellTotalTexto = new PdfPCell(new Phrase("TOTAL GERAL", fontNegrito));
            cellTotalTexto.setColspan(3);
            cellTotalTexto.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellTotalTexto.setPadding(5);
            tableItens.addCell(cellTotalTexto);

            PdfPCell cellTotalValor = new PdfPCell(new Phrase(String.format("R$ %.2f", orcamento.getValorTotal()), fontTotal));
            cellTotalValor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellTotalValor.setPadding(5);
            tableItens.addCell(cellTotalValor);

            document.add(tableItens);

            // ==========================================
            // 4. RODAPÉ E ASSINATURAS
            // ==========================================
            document.add(new Paragraph("\nOBS: VALOR DA RECARGA DOS EXTINTORES FECHADO, NÃO HAVERÁ NENHUM ACRÉSCIMO NAS TROCAS DE PEÇAS\n\n", fontPequena));           

            // Condição de pagamento digitada no app
            String obsPagamento = (orcamento.getObservacoes() != null && !orcamento.getObservacoes().isEmpty()) 
                                ? orcamento.getObservacoes() 
                                : "À VISTA";
            document.add(new Paragraph("Condições Pagamento: " + obsPagamento + "\n\n", fontNormal));

            document.add(new Paragraph("Atenciosamente,\n\n", fontNormal));

            document.add(new Paragraph("IMPACTO EXTINTORES LTDA\n", fontRodape));
            // Nome do vendedor logado e certificado Inmetro
            String nomeVendedor = orcamento.getUsuarioResponsavel() != null ? orcamento.getUsuarioResponsavel().toUpperCase() : "VENDEDOR IMPACTO";
            document.add(new Paragraph(nomeVendedor, fontRodape));
            document.add(new Paragraph(" "));

            PdfPTable tableObs = new PdfPTable(1);
            tableObs.setWidthPercentage(100);
            PdfPCell cellObs = new PdfPCell(new Phrase("Empresa credenciada INMETRO/IPEM nº 012259/2025, atuando conforme as normas técnicas vigentes.", fontNegrito));
            cellObs.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cellObs.setVerticalAlignment(Element.ALIGN_MIDDLE);   
            cellObs.setPadding(8f);       
            cellObs.setBackgroundColor(Color.lightGray);                       
            cellObs.setBorderWidth(1.5f);                         
            cellObs.setBorderColor(Color.BLACK); 
            tableObs.addCell(cellObs);
            document.add(tableObs);  
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return baos.toByteArray();
    }

    // Métodos Auxiliares para facilitar a criação de células de tabela
    private void adicionarCelulaCliente(PdfPTable table, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(5);
        cell.setBorderColor(Color.BLACK);
        table.addCell(cell);
    }

    private void adicionarCelulaTabela(PdfPTable table, String texto, Font font, Color bgColor, int alinhamento) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(5);
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(alinhamento);
        cell.setBorderColor(Color.BLACK);
        table.addCell(cell);
    }

    private void adicionarCelulaClienteMista(PdfPTable table, String texto1, Font font1, String texto2, Font font2) {
    // Cria a frase vazia
    Phrase phrase = new Phrase();
    
    // Adiciona o primeiro pedaço (Ex: "CLIENTE: " com fonte normal)
    phrase.add(new Chunk(texto1, font1));
    
    // Adiciona o segundo pedaço (Ex: O Nome com fonte negrito e sublinhado)
    phrase.add(new Chunk(texto2 != null ? texto2 : "", font2));
    
    // Coloca na célula
    PdfPCell cell = new PdfPCell(phrase);
    cell.setPadding(5);
    cell.setBorderColor(Color.BLACK);
    table.addCell(cell);
    }
}