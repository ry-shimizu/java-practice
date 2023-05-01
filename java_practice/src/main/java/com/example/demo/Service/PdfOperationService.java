package com.example.demo.Service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.CSSResource;

import java.awt.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    // 固定ファイルをダウンロードする(別方法 コントローラーからレスポンス返却)
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

    // pdfを作成して、ダウンロードさせる
    public ResponseEntity<byte[]> createPdf() {
        try (var reader = new BufferedReader(new FileReader(Paths.get("テストファイル.txt").toFile()))) {
            // 一時ファイルの作成処理
            var file = Files.createTempFile("temp", ".pdf").toFile();

            var document = new Document();
            var pdfWriter = PdfWriter.getInstance(document, new BufferedOutputStream(new FileOutputStream(file)));

            // ここで日本語を利用できないので、上記で日本語のフォントが利用できるように設定
            // 引数(フォント、PDF出力オプション日本語、PDFにフォントを埋め込むかどうかファイルサイズは大きくなる)
            var baseFont = BaseFont.createFont("HeiseiMin-W3", "UniJIS-UCS2-H", BaseFont.NOT_EMBEDDED);
            // タイトル用
            var titleFont = new Font(baseFont, 20f, Font.ITALIC, Color.RED);
            // 本文用
            var font = new Font(baseFont, 12f, java.awt.Font.PLAIN, Color.BLACK);
            document.open();
            var title = new Paragraph("PDF作成のタイトルです", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(Chunk.NEWLINE));
            String line = "";
            while ((line = reader.readLine()) != null) {
                document.add(new Paragraph(line, font));
            }
            // closeする順番も重要。先にdocumentを閉じる
            document.close();
            pdfWriter.close();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    // ファイル名も日本語不可 URL
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode("テスト.pdf", StandardCharsets.UTF_8))
                    .body(new BufferedInputStream(new FileInputStream(file)).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("ファイル操作で問題発生", e);
        } catch (DocumentException e) {
            throw new RuntimeException("ドキュメント操作で問題発生", e);
        }
    }

    // htmlを読み込んでPDF化、インラインで
    public void usedHtmlPdf(HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline");
        var htmlFile = Paths.get("PDF関連/テストhtml.html");
        try (var res = response.getOutputStream()) {
            String stringHtml = null;
            if (htmlFile.toFile().exists()) {
                stringHtml = Files.readString(htmlFile, StandardCharsets.UTF_8);
            } else {
                throw new RuntimeException("ファイルが存在していない");
            }
           var render = new ITextRenderer();
            render.setDocumentFromString(stringHtml);
            render.layout();
            render.createPDF(res);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("ファイル操作で問題発生", e);
        }
    }

    // zipの中にpdfを作成して、ダウンロードさせる

}
