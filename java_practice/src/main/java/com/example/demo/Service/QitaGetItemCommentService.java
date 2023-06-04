package com.example.demo.Service;

import com.example.demo.Config.Constant;
import com.example.demo.Request.QitaWriteReactionsRequest;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class QitaGetItemCommentService {

    public QitaGetItemCommentDto[] qitaGetItemComment(QitaWriteReactionsRequest request) {
        var url = new StringBuilder()
                .append(Constant.qitaBaseUrl)
                .append("/api/v2/items/")
                .append(request.getItemId())
                .append("/comments").toString();
        var restTemplate = new RestTemplate();

        try {
            var response = restTemplate.getForEntity(url, QitaGetItemCommentDto[].class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RestClientException("400系エラーが発生しました", e);
        } catch (HttpServerErrorException e) {
            throw new RestClientException("500系エラーが発生しました", e);
        }
    }

    @Data
    public static class QitaGetItemCommentDto {
        private String body;
        private String id;
        private String createdAt;
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
