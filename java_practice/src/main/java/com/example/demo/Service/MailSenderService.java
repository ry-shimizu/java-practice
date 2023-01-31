package com.example.demo.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.xml.crypto.MarshalException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final MailSender mailSender;

    public boolean sendMail() throws Exception {
        try{
            var mailInfo = new SimpleMailMessage();
            mailInfo.setSubject("javaの実装練習です");
            mailInfo.setText("お元気ですかテストです");
            // テスト用メールアドレス
            mailInfo.setTo("nagahisawebpage@gmail.com");
            mailInfo.setFrom("ryouya56395639@gmail.com");
            mailSender.send(mailInfo);
            return true;
        } catch (MailException e) {
            throw new Exception("メール送信に失敗しました", e);
        }
    }
}
