package com.fernandodev.infrastructure.etiqueta;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class ProdutoEtiqueta {

    public static void gerarEtiquetas(String sku, int quantidade, String outputFile) {
        try {
            Document document = new Document(PageSize.A4, 20, 20, 20, 20);
            PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();

            int colunas = 2; // 2 colunas por linha
            PdfPTable tabela = new PdfPTable(colunas);
            tabela.setWidthPercentage(100);
            tabela.getDefaultCell().setBorder(Rectangle.NO_BORDER); // sem borda

            for (int i = 0; i < quantidade; i++) {
                // Cria célula para cada etiqueta
                PdfPCell celula = new PdfPCell();
                celula.setBorder(Rectangle.NO_BORDER);
                celula.setHorizontalAlignment(Element.ALIGN_CENTER);

                // Código de barras
                BitMatrix matrix = new MultiFormatWriter()
                        .encode(sku, BarcodeFormat.CODE_128, 200, 50);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(matrix, "PNG", baos);

                Image barcode = Image.getInstance(baos.toByteArray());
                barcode.scaleToFit(200, 50);
                barcode.setAlignment(Element.ALIGN_CENTER);
                celula.addElement(barcode);

                // Texto do SKU
                Paragraph p = new Paragraph("SKU: " + sku);
                p.setAlignment(Element.ALIGN_CENTER);
                p.setSpacingBefore(1f); // distância pequena
                p.setSpacingAfter(15f);
                celula.addElement(p);

                tabela.addCell(celula);
            }

            // Se a última linha não estiver completa, preenche células vazias
            int resto = quantidade % colunas;
            if (resto != 0) {
                for (int j = 0; j < colunas - resto; j++) {
                    PdfPCell vazio = new PdfPCell();
                    vazio.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(vazio);
                }
            }

            document.add(tabela);
            document.close();
            System.out.println("PDF de etiquetas gerado em: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
