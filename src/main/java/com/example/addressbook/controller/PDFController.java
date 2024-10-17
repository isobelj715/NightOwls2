package com.example.addressbook.controller;

import com.example.addressbook.model.Art;
import com.example.addressbook.model.Portfolio;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.util.List;

public class PDFController {

        public void generatePdf(String dest, Portfolio portfolio, List<Art> artPieces) throws Exception {
            // Using Itext Library
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            // Portfolio Section
            document.add(new Paragraph("Portfolio: " + portfolio.getPortfolioName()).setFont(boldFont).setFontSize(18).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Description: " + portfolio.getPortfolioDescription()).setFont(regularFont).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(" "));

            // Art Details
            for (Art piece : artPieces) {

                if (piece.getFilePath() != null) {
                    try {
                        ImageData imageData = ImageDataFactory.create(piece.getFilePath());
                        Image image = new Image(imageData);
                        image.setAutoScale(true);  // Scale image to fit
                        document.add(image);
                    } catch (Exception e) {
                        document.add(new Paragraph("Image not available"));
                    }
                }

                document.add(new Paragraph("Title: " + piece.getArtTitle()).setFont(boldFont).setFontSize(18));
                document.add(new Paragraph("Year: " + piece.getYear()).setFont(regularFont).setFontSize(14));
                if (piece.getCategory() != null) document.add(new Paragraph("Category: " + piece.getCategory()).setFont(regularFont).setFontSize(14));
                if (piece.getMedium() != null) document.add(new Paragraph("Medium: " + piece.getMedium()).setFont(regularFont).setFontSize(14));
                if (piece.getMaterial() != null) document.add(new Paragraph("Material: " + piece.getMaterial()).setFont(regularFont).setFontSize(14));
                document.add(new Paragraph("Dimensions: " + piece.getWidth() + "x" + piece.getHeight() + "x" + piece.getDepth() + " " + piece.getUnits()).setFont(regularFont).setFontSize(14));
                document.add(new Paragraph("Description: " + piece.getDescription()).setFont(regularFont).setFontSize(14));

                document.add(new Paragraph(" "));
            }

            // Close document
            document.close();
        }
    }


