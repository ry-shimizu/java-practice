package com.example.demo.Service;

import com.example.demo.config.MailConfig;
import com.example.demo.config.MailConfigValue;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;


@Service
@RequiredArgsConstructor
@Slf4j
public class MailSenderService {

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;
    private final MailConfig mailConfig;
    private final MailConfigValue mailConfigValue;

    public boolean sendMail() {
            var mailInfo = new SimpleMailMessage();
            mailInfo.setSubject("javaの実装練習です");
            mailInfo.setText("お元気ですかテストです");
            // テスト用メールアドレス
            mailInfo.setTo("nagahisawebpage@gmail.com");
            mailInfo.setFrom("ryouya56395639@gmail.com");
        // 非検査例外のため、try-catch文を書かなくてもよい。
        // 基本エラーを見れば何が起きたかは分かるが、メッセージをログに残す場合は書いてもよい？
        try{
            mailSender.send(mailInfo);
            return true;
        } catch (MailException e) {
            throw new RuntimeException("メール送信に失敗しました", e);
        }
    }

    public boolean javaMailSender() {
        // 新しいメッセージを作成
        var message = javaMailSender.createMimeMessage();
        try {
            var messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom("ryouya56395639@gmail.com");
            messageHelper.setTo("nagahisawebpage@gmail.com");
            messageHelper.setText("お元気ですかテストです。JavaMail利用です",
                    "<span style='color: red'>赤文字出力</span><br>段落下げた");
            messageHelper.setSubject("タイトルですよ");

            javaMailSender.send(message);
            return true;
            // MessagingExceptionは、検査例外なので必ずtry-catchが必要
        } catch(MessagingException e) {
            // メール送信失敗で処理を終えるのであれば、throw
            throw new RuntimeException("メッセージの設定に失敗しました", e);
            // 終わらせず、処理を続けるのであれば、スタックトレースを残す
            // e.printStackTrace();
        }
    }

    public boolean smtpMailSender() {
        var properties = new Properties();
        properties.put("mail.host", mailConfig.getHost());
        properties.put("mail.smtp.host", mailConfig.getHost());
        properties.put("mail.smtp.port", mailConfig.getPort());
        properties.put("mail.smtp.auth", mailConfigValue.isAuth());
        properties.put("mail.smtp.starttls.enable","true");

       class smtpAuth extends Authenticator {
           @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.getUserName(), mailConfig.getPassword());
            }
        }
        // メールセッションの確立
        var session = Session.getInstance(properties, new smtpAuth());

       // メッセージ部分の作成
        var message = new MimeMessage(session);
        try {
            // message.setFrom("ryouya56395639@gmail.com"); メールのFROMにメールアドレスだけでなく、文字を入れたい時下のを利用
            message.setFrom(new InternetAddress("ryouya56395639", "テスト"));
            message.setText("javaのメール送信テスト");
            message.setSubject("テストで送ります");
            message.setRecipients(Message.RecipientType.TO, "nagahisawebpage@gmail.com");
            message.setRecipients(Message.RecipientType.CC, "shigeta@gmail.com");
            Transport.send(message);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("smtpメール送信エラー", e);
        }
    }
}
