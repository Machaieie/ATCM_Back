package com.bussinessmanagement.managementSystem.Services;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.bussinessmanagement.managementSystem.Models.Ticket;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;

@Service
public class TicketPdfService {

    public byte[] gerarPdf(Ticket ticket, byte[] qrCode) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Título
        Paragraph title = new Paragraph("EVENT TICKET")
                .setBold()
                .setFontSize(24)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);

        document.add(title);
        document.add(new Paragraph("\n"));

        // Informações do evento
        document.add(new Paragraph("Evento: " + ticket.getEvent().getName()).setBold());
        document.add(new Paragraph("Local: " + ticket.getEvent().getLocation()));
        document.add(new Paragraph("Data: " + ticket.getEvent().getDate() + " " + ticket.getEvent().getTime()));

        document.add(new Paragraph("\n"));

        // Informações do cliente
        document.add(new Paragraph("Cliente: " + ticket.getClient().getFullName()));
        document.add(new Paragraph("Tipo de Bilhete: " + ticket.getTipo()));
        document.add(new Paragraph("Código do Bilhete: " + ticket.getQrCode()));

        document.add(new Paragraph("\n"));

        // QR Code
        ImageData imageData = ImageDataFactory.create(qrCode);
        Image qrImage = new Image(imageData);

        qrImage.setWidth(200);
        qrImage.setHeight(200);
        qrImage.setHorizontalAlignment(
                com.itextpdf.layout.properties.HorizontalAlignment.CENTER);

        document.add(qrImage);

        document.add(new Paragraph("\n"));

        // Rodapé
        Paragraph footer = new Paragraph("Apresente este QR Code na entrada do evento")
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setFontSize(10);

        document.add(footer);

        document.close();

        return baos.toByteArray();
    }
}