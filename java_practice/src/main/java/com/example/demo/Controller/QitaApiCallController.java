package com.example.demo.Controller;

import com.example.demo.Request.QitaWriteReactionsRequest;
import com.example.demo.Response.QitaGetItemCommentResponse;
import com.example.demo.Response.QitaWriteReactionsResponse;
import com.example.demo.Service.QitaGetItemCommentService;
import com.example.demo.Service.QitaWriteReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QitaApiCallController {

    private final QitaWriteReactionService qitaWriteReactionService;

    private final QitaGetItemCommentService qitaGetItemCommentService;

    @PostMapping("/writeReactions")
    public QitaWriteReactionsResponse writeReactions(@RequestBody @Validated QitaWriteReactionsRequest request) {
        return new QitaWriteReactionsResponse(qitaWriteReactionService.WriteReactions(request));
    }

    @PostMapping("/getItemComment")
    public QitaGetItemCommentResponse qitaGetItemComment(@RequestBody @Validated QitaWriteReactionsRequest request) {
        return new QitaGetItemCommentResponse(qitaGetItemCommentService.qitaGetItemComment(request));
    }
}