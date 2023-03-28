package com.example.demo.Service;

import com.example.demo.Request.AsyncRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncService {
    private final MailSenderService mailSenderService;

    // Runnableインターフェース、Threadクラス利用
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

    // シングルスレッド Executors利用
    public AsyncDto useExecutors(AsyncRequest request, LocalDateTime now) {
        var thread = Executors.newSingleThreadExecutor();
        Future<String> result = thread.submit(new commonTask(), request.getMessage());
        try {
            log.info(result.get());
        } catch (ExecutionException e) {
            throw new RuntimeException(now + "非同期処理中に問題発生", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(now + "←の時刻で割り込み処理がありました", e);
        }
        return AsyncDto.builder()
                .status("success")
                .result(true)
                .time(now)
                .build();
    }

    // ThreadPool利用
    public AsyncDto useThreadPool(AsyncRequest request, LocalDateTime now) {
        var threadPool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                var thread = new Thread(r);
                thread.setName("useThreadPool" + request.getActiveNum());
                thread.setDaemon(true);
                return thread;
            }
        });

        class callable implements Callable<String> {
            @Override
            public String call() {
                IntStream.iterate(0, a -> a+1).limit(5).mapToObj(b -> b*2 + ",").forEach(log::info);
                return "finish→" + Thread.currentThread().getName() + "、スレッドID" + Thread.currentThread().getId();
            }
        }

        Future<String> result = threadPool.submit(new callable());
        Future<String> result2 = threadPool.submit(new callable());
        threadPool.execute(new commonTask());
        try {
            log.info(result.get());
            log.info(result2.get());
        } catch (ExecutionException e) {
            throw new RuntimeException(now + "非同期処理中に問題発生", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(now + "←の時刻で割り込み処理がありました", e);
        }

        return AsyncDto.builder()
                .status("success")
                .result(true)
                .time(now)
                .build();
    }


    // 共通タスク定義クラス
    private static class commonTask implements Runnable {
        @Override
        public void run(){
            log.info("共通タスク開始します");
            Optional<String> a = Optional.empty();
            Optional<String> b = Optional.of("abcde");
            Optional<String> c = Optional.ofNullable(null);
            a = b.map(String::toUpperCase);

            List<String> list = Stream.of(a,b,c).map(op -> op.orElse("置換")).toList();
            Set<String> set = Stream.of(a,b,c).map(op -> op.orElse("置換")).filter(fil -> fil.endsWith("e")).collect(Collectors.toSet());

            list.forEach(log::info);
            set.forEach(log::info);
            log.info(Thread.currentThread().getId() + Thread.currentThread().getName() + "← 終了します");
        }
    }


    @Data
    @Builder
    public static class AsyncDto {
        public String status;
        public boolean result;
        public LocalDateTime time;
    }
}
