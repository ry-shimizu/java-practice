package com.example.demo.Service;

import com.example.demo.Config.Constant;
import com.example.demo.Request.RequestSorce.QitaDeleteCommentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class QitaDeleteCommentService {

    public ResponseEntity<Void> qitaDeleteComment(QitaDeleteCommentRequest request) {

        var url = new StringBuilder()
                .append(Constant.qitaBaseUrl)
                .append("/api/v2/comments/")
                .append(request.getCommentId())
                .toString();

        var client = HttpClient.newHttpClient();

        var requestDetail = HttpRequest.newBuilder(
                URI.create(url))
                .DELETE()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + "")
                .timeout(Duration.ofMillis(5000))
                .build();

        try {
            var response = client.send(requestDetail, HttpResponse.BodyHandlers.discarding());
            return ResponseEntity.status(response.statusCode()).build();
        } catch (IOException | InterruptedException e) {
            throw new RestClientException("通信エラーもしくは割り込みが発生しました", e);
        }
    }
}
