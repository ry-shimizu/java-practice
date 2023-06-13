package com.example.demo.Service;

import com.example.demo.Config.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class QitaUpdateCommentService {

    public QitaWriteReactionService.QitaWriteReactionsDto qitaUpdateComment(String commentId) {
        var url = new StringBuilder()
                .append(Constant.qitaBaseUrl)
                .append("/api/v2/comments/")
                .append(commentId)
                .toString();

        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(
                URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString("コメントの更新"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + "")
                .timeout(Duration.ofMillis(3000))
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var body = response.body();
            var objectMapper = new ObjectMapper();
            return objectMapper.readValue(body, QitaWriteReactionService.QitaWriteReactionsDto.class);
        } catch (IOException | InterruptedException e) {
            throw new RestClientException("通信エラーもしくは割り込みが発生しました", e);
        }

    }
}
