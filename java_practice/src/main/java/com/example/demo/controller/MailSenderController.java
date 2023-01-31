package com.example.demo.controller;

import com.example.demo.Response.MailSenderResponse;
import com.example.demo.Service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class MailSenderController {

   private final MailSenderService mailSenderService;

    @PostMapping("/mailSender")
    public MailSenderResponse MailSend() throws Exception {
        var result = mailSenderService.sendMail();
        return new MailSenderResponse("success", result);
    }

}
