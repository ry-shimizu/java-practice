package com.example.demo.Service;

import com.example.demo.Config.Constant;
import com.example.demo.Request.QitaWriteReactionsRequest;
import com.example.demo.Request.RequestSorce.QitaWriteReactionsResource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class QitaWriteReactionService {

    // restTemplete利用 TimeOutの時はリトライする処理を追加する
    public QitaWriteReactionsDto WriteReactions(QitaWriteReactionsRequest request) {
        // url作成
        var url = new StringBuilder()
                .append(Constant.qitaBaseUrl)
                .append("/api/v2/items/")
                .append(request.getItemId())
                .append("/comments").toString();
        // リクエストボディ作成
        var requestBody = new QitaWriteReactionsResource("テストでコメントします");

        var requestEntity = RequestEntity
                .post(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "")
                .body(requestBody);

        var restTemplate = new RestTemplate();
        try {
            var response = restTemplate.exchange(requestEntity, QitaWriteReactionsDto.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RestClientException("400系エラーが発生しました", e);
        } catch (HttpServerErrorException e) {
            throw new RestClientException("500系エラーが発生しました", e);
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