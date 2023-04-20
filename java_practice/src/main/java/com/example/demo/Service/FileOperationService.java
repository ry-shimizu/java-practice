package com.example.demo.Service;

import com.example.demo.Request.FileOperationRequest;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class FileOperationService {

    // 効率は悪いが学習のため
    public void fileReader() {
        var text = new StringBuilder();
        var i = 0;
        try (var fileReader =
                     new FileReader("テストファイル.txt")) {
            while ((i = fileReader.read()) != -1) {
                  text.append((char) i);
             }
            log.info(text.toString());
        } catch (IOException e) {
            throw new RuntimeException("ファイル操作で問題が発生しました", e);
        }
    }

    // 効率は悪いが学習のため
    public void fileWriter(FileOperationRequest request) {
        var path = Paths.get("テストファイル.txt");
        if(!Files.exists(path)) {
            try {
                Files.createFile(path);
                try (var fileWriter = new FileWriter(path.toFile())) {
                    fileWriter.write(request.getInput());
                }
            } catch(IOException e) {
                throw new RuntimeException("ファイル作成で問題が発生しました", e);
            }
        } else {
            try (var fileWriter = new FileWriter(path.toFile(), true)) {
                fileWriter.write(request.getInput());
            } catch(IOException e) {
                throw new RuntimeException("ファイル作成で問題が発生しました", e);
            }
        }
    }

    public FileOperationDto bufferedReader() {
        StringBuilder response = new StringBuilder();
        var path = Paths.get("テストファイル.txt");
        if(Files.exists(path)) {
            try(var reader = new BufferedReader(new FileReader(path.toFile()))) {
                var s = "";
                while ((s = reader.readLine()) != null) {
                    response.append(s);
                }
            } catch (IOException e) {
                throw new RuntimeException("ファイル操作で問題が発生しました", e);
            }
        }
        return FileOperationDto.builder()
                .content(response.toString())
                .build();
    }

    public void bufferedWriter(FileOperationRequest request) {
        if(Stream.of("jpeg", "png", "jpg").anyMatch(a -> request.getInput().endsWith(a))) {
            var pathOutput = Paths.get("テストファイル画像" + request.getInput().substring(request.getInput().lastIndexOf(".")));
            var pathInput = Paths.get(request.getInput());
            if(Files.exists(pathInput)) {
                // このパスファイルから先にデータを読み出して、書き出す処理
                try(var output = new BufferedOutputStream(new FileOutputStream(pathOutput.toFile()));
                    var input = new BufferedInputStream(new FileInputStream(pathInput.toFile()))) {
                    // output.write(Files.readAllBytes(pathInput)); このやり方もあるが、1度に読み込むのはメモリ不足になるかもしれない
                    var data = new byte[1024];
                    int len;

                    while((len = input.read(data)) > 0) {
                        output.write(data, 0, len);

                    }
                    output.flush();

                } catch (IOException e) {
                    throw new RuntimeException("ファイル操作で問題が発生しました", e);
                }
            }
        } else {
            var path3 = Paths.get("テストファイル.txt");
            try(var writer = new BufferedWriter(new FileWriter(path3.toFile(), true))) {
                writer.write(request.getInput());
                writer.newLine();
                writer.write(request.getInput());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException("ファイル操作で問題が発生しました", e);
            }
        }
    }


    public void zipStream(FileOperationRequest request) {
        var path = Paths.get(request.getInput());
        var zipFilePath = Paths.get("/Users/shimizuryouya/Desktop/テストまとめ.zip");
        if (Files.exists(path)) {
            try (var zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilePath.toFile())))) {
                if (Files.isDirectory(path)) {
                    directoryZip(path.getNameCount(), path, zip);
                } else {
                    // 新しいファイル名を指定し、zip中に設定
                    zip.putNextEntry(new ZipEntry(path.getFileName().toString()));
                    zip.write(Files.readAllBytes(path));
                }
            } catch (IOException e) {
                throw new RuntimeException("ファイル操作で問題が発生しました。", e);
            }
        }
    }

    private void directoryZip(int rootCount, Path path, ZipOutputStream zip) throws IOException {
        Files.list(path).forEach(
                p -> {
                    try {
                        var pathName = p.subpath(rootCount, p.getNameCount());
                        if (Files.isDirectory(p)) {
                            zip.putNextEntry(new ZipEntry(pathName + "/"));
                            directoryZip(rootCount, p, zip);
                        } else {
                            zip.putNextEntry(new ZipEntry(pathName.toString()));
                            zip.write(Files.readAllBytes(p));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("ファイル操作で問題が発生しました。", e);
                    }
                });
    }

    @Data
    @Builder
    public static class FileOperationDto {
        private String content;
    }
}
