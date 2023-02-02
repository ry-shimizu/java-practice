package com.example.demo.controller;

import com.example.demo.Response.MailSenderResponse;
import com.example.demo.Service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class MailSenderController {

   private final MailSenderService mailSenderService;

    @PostMapping("/mailSender")
    public ResponseEntity<MailSenderResponse> mailSend() {
        var result = mailSenderService.sendMail();
        return ResponseEntity.ok().body(new MailSenderResponse("success", result));
        // どっちでも返却値は同じ 意図的に404返したい時などに利用？
        // return new MailSenderResponse("success", result);
    }

    @PostMapping("/javaMailSender")
    public ResponseEntity<MailSenderResponse> javaMailSender() {
        var result = mailSenderService.javaMailSender();
        return ResponseEntity.ok().body(new MailSenderResponse("success", true));
    }

}
