package com.example.demo.Controller;

import com.example.demo.Request.QitaWriteReactionsRequest;
import com.example.demo.Response.QitaWriteReactionsResponse;
import com.example.demo.Service.QitaApiCallService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QitaApiCallController {

    private final QitaApiCallService qitaApiCallService;

    @PostMapping("/writeReactions")
    public QitaWriteReactionsResponse writeReactions(@RequestBody @Validated QitaWriteReactionsRequest request) {
        return new QitaWriteReactionsResponse(qitaApiCallService.WriteReactions(request));
    }
}
