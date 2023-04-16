package com.example.demo.Service;

import com.example.demo.Request.FileOperationRequest;
import com.example.demo.Response.FileOperationResponse;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        var path = Paths.get("テストファイル.txt");
        try(var writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(request.getInput());
            writer.newLine();
            writer.write(request.getInput());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("ファイル操作で問題が発生しました", e);
        }
    }

    @Data
    @Builder
    public static class FileOperationDto {
        private String content;
    }
}
