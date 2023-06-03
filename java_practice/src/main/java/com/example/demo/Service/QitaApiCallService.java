package com.example.demo.Service;

import com.example.demo.Request.QitaWriteReactionsRequest;
import com.example.demo.Request.RequestSorce.QitaWriteReactionsResource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class QitaApiCallService {
    private String baseUrl = "https://qiita.com";

    // restTemplete利用
    public QitaWriteReactionsDto WriteReactions(QitaWriteReactionsRequest request) {
        // url作成
        var url = new StringBuilder()
                .append(baseUrl)
                .append("/api/v2/items/")
                .append(request.getItemId())
                .append("/comments").toString();
        // リクエストボディ作成
        var requestBody = new QitaWriteReactionsResource("テストでコメントします");

        RequestEntity<QitaWriteReactionsResource> requestEntity = RequestEntity
                .post(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "")
                .body(requestBody);
        var restTemplate = new RestTemplate();
        try {
            var response = restTemplate.exchange(requestEntity, QitaWriteReactionsDto.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new RestClientException("qitaとの通信で何かしらエラーが発生しました", e);
        }
    }


    @Data
    public static class QitaWriteReactionsDto {
        private String body;
        private String id;
        private String updatedAt;
        private User user;
    }
    @Data
    public static class User {
        private String id;
        private int itemsCount;
        private String name;
        private String githubLoginName;
        private String description;
    }
}
