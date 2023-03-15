package com.example.demo.Service;

import com.example.demo.Request.AsyncRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncService {
    private final MailSenderService mailSenderService;

    public AsyncDto normalAsync(AsyncRequest request, LocalDateTime now) {
        Runnable task =  () -> {
            log.info("非同期Runnableはじめます。");
            var thread = Thread.currentThread();

            List<String> list = Stream.of("わ","た","し").toList();
            List<String> list2 = List.of("お","お","れ");
            List<Integer> list3 = Arrays.asList(1,2);

            list.stream().filter(a -> a.equals("わ")).forEach(log::info);
            log.info(list2.stream().distinct().findAny().orElse("おれ"));
            log.info(String.valueOf(list3.stream().map(c -> c * 15).max(Comparator.naturalOrder()).get()));

            log.info(thread.getName() + thread.getId() + "←終わります");
        };

        class Thread2 extends Thread {
            @Override
            public void run() {
                log.info("非同期Treadはじめます。");
                var thread = Thread.currentThread();
                mailSenderService.sendMail();
                log.info(thread.getName() + thread.getId() + "←終わります");
            }
        }
        new Thread(task).start();
        new Thread2().start();

        log.info("メインスレッドは終わります");

        // レスポンスをサービス側で可変にできるように練習
        return AsyncDto.builder()
                .status("success")
                .result(true)
                .build();
    }


    @Data
    @Builder
    public static class AsyncDto {
        public String status;
        public  boolean result;
    }
}
