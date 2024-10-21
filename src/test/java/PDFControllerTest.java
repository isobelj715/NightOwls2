import com.example.addressbook.controller.PDFController;
import com.example.addressbook.model.Art;
import com.example.addressbook.model.Portfolio;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.junit.jupiter.api.Test;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PDFControllerTest {

        private PDFController pdfController;
        private File tempPdfFile;

        @BeforeEach
        public void setUp() throws Exception {
            pdfController = new PDFController();

            tempPdfFile = File.createTempFile("testPortfolio", ".pdf");
        }

        @AfterEach
        public void tearDown() {
            if (tempPdfFile.exists()) {
                tempPdfFile.delete();
            }
        }

        @Test
        public void testGeneratePdfCreatesFile() throws Exception {
            Portfolio portfolio = new Portfolio("Test Portfolio", "This is a test portfolio.", 1);
            Art artwork1 = new Art("Artwork 1", 2021);
            artwork1.setMedium("Oil on canvas");
            Art artwork2 = new Art("Artwork 2", 2022);
            artwork2.setMedium("Acrylic on wood");

            pdfController.generatePdf(tempPdfFile.getAbsolutePath(), portfolio, List.of(artwork1, artwork2));

            assertTrue(tempPdfFile.exists());
            assertTrue(tempPdfFile.length() > 0);  // File should not be empty
        }

        @Test
        public void testGeneratePdfContainsPortfolioTitle() throws Exception {
            // Arrange
            Portfolio portfolio = new Portfolio("Test Portfolio", "This is a test portfolio.", 1);
            Art artwork1 = new Art("Artwork 1", 2021);
            artwork1.setMedium("Oil on canvas");
            Art artwork2 = new Art("Artwork 2", 2022);
            artwork2.setMedium("Acrylic on wood");

            // Act
            pdfController.generatePdf(tempPdfFile.getAbsolutePath(), portfolio, List.of(artwork1, artwork2));

            try (PdfReader reader = new PdfReader(new FileInputStream(tempPdfFile));
                 PdfDocument pdfDoc = new PdfDocument(reader)) {

                String extractedText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1));

                assertTrue(extractedText.contains("Test Portfolio"));
            }
        }

        @Test
        public void testGeneratePdfContainsArtworkTitles() throws Exception {
            Portfolio portfolio = new Portfolio("Test Portfolio", "This is a test portfolio.", 1);
            Art artwork1 = new Art("Artwork 1", 2021);
            artwork1.setMedium("Oil on canvas");
            Art artwork2 = new Art("Artwork 2", 2022);
            artwork2.setMedium("Acrylic on wood");

            pdfController.generatePdf(tempPdfFile.getAbsolutePath(), portfolio, List.of(artwork1, artwork2));

            try (PdfReader reader = new PdfReader(new FileInputStream(tempPdfFile));
                 PdfDocument pdfDoc = new PdfDocument(reader)) {

                String extractedText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1));

                assertTrue(extractedText.contains("Artwork 1"));
                assertTrue(extractedText.contains("Artwork 2"));
            }
        }

        @Test
        public void testGeneratePdfHandlesEmptyArtworks() throws Exception {

            Portfolio portfolio = new Portfolio("Test Portfolio", "This is a test portfolio.", 1);

            pdfController.generatePdf(tempPdfFile.getAbsolutePath(), portfolio, List.of());

            try (PdfReader reader = new PdfReader(new FileInputStream(tempPdfFile));
                 PdfDocument pdfDoc = new PdfDocument(reader)) {

                String extractedText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1));

                assertTrue(extractedText.contains("Test Portfolio"));
            }
        }
    }

