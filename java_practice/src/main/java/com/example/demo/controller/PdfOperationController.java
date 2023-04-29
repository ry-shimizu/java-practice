package com.example.demo.controller;

import com.example.demo.Service.PdfOperationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class PdfOperationController {
    final PdfOperationService pdfOperationService;
    @GetMapping("/pdfPreview")
    public void pdfPreview(HttpServletResponse response) {
        pdfOperationService.pdfPreview(response);
    }

    @GetMapping("/pdfDl")
    public void pdfDl(HttpServletResponse response) {
        pdfOperationService.pdfDl(response);
    }

    @GetMapping("/otherPdfDl")
    public ResponseEntity<byte[]> otherPdfDl(HttpServletResponse response) {
        return pdfOperationService.otherPdfDl(response);
    }

    @GetMapping("/createPdf")
    public ResponseEntity<byte[]> createPdf() {
        return pdfOperationService.createPdf();
    }

}
