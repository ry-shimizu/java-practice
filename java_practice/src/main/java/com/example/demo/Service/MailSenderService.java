package com.example.demo.Service;

import com.example.demo.config.MailConfig;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;
    private final MailConfig mailConfig;

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
        System.out.print(mailConfig.getHost());
        return true;
    }
}
