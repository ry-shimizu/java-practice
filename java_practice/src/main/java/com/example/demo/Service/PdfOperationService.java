package com.example.demo.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PRIndirectReference;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Service
public class PdfOperationService {

    // 固定ファイルをプレビューするだけ
    public void pdfPreview(HttpServletResponse response) {
        var input = Paths.get("/Users/shimizuryouya/Desktop/ファイル操作テスト/テストファイル.pdf");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline");
        response.setHeader("pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        try(var inputStream = new BufferedInputStream(new FileInputStream(input.toFile()));
            var res = response.getOutputStream()) {
            res.write(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("ファイル操作で問題発生", e);
        }
    }

    // プレビューではなく、固定ファイルをダウンロードする
    public void pdfDl(HttpServletResponse response) {
        var input = Paths.get("/Users/shimizuryouya/Desktop/ファイル操作テスト/テストファイル.pdf");

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename= " + URLEncoder.encode(String.valueOf(input.getFileName()), StandardCharsets.UTF_8));
        response.setHeader("pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        try (var inputStream = new BufferedInputStream(new FileInputStream(input.toFile()));
             var res = response.getOutputStream()) {
            res.write(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("ファイル操作で問題発生", e);
        }
    }

    // 固定ファイルをダウンロードする(別方法)
    public ResponseEntity<byte[]> otherPdfDl(HttpServletResponse response) {
        var input = Paths.get("/Users/shimizuryouya/Desktop/ファイル操作テスト/テストファイル.pdf");
        var respHeader = new HttpHeaders();
        respHeader.setContentType(MediaType.APPLICATION_PDF);
        respHeader.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sample.pdf");

        try (var inputStream = new BufferedInputStream(new FileInputStream(input.toFile()))) {
            return new ResponseEntity<byte[]>(inputStream.readAllBytes(), respHeader, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException("ファイル操作で問題発生", e);
        }
    }

    // pdfを作成して、プレビューもしくはダウンロードさせる
    public ResponseEntity<byte[]> createPdf() {
        try (var inputStream = new BufferedInputStream(new FileInputStream(Paths.get("テストファイル.txt").toFile()))) {
            var document = new Document();
            var file = Paths.get("test.pdf").toFile();
            var pdfWriter = PdfWriter.getInstance(document, new BufferedOutputStream(new FileOutputStream(file)));
            document.open();
            var font = new Font(BaseFont.createFont("HeiseiMin-W3", "UniJIS-UCS2-H", BaseFont.EMBEDDED));
            // ここで日本語を利用できないので、上記で日本語のフォントが利用できるように設定
            // 引数(フォント、PDF出力オプション日本語、PDFにフォントを埋め込むかどうかファイルサイズは大きくなる)
            document.add(new Paragraph("日本語使えてる？", font));
            document.close();
            pdfWriter.close();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                    .body(new BufferedInputStream(new FileInputStream(file)).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("ファイル操作で問題発生", e);
        }
    }

    // zipの中にpdfを作成して、ダウンロードさせる

}
