package com.example.demo.Service;

import com.example.demo.Request.AsyncRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
    private final UseThreadPoolTaskTest useThreadPoolTaskTest;

    @Autowired
    @Qualifier("Thread2")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

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
            log.info("必ず上記スレッドの結果get待ち");
            thread.shutdown();
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
        threadPool.execute(new commonTask());
        threadPool.execute(new commonTask());
        Future<String> result2 = threadPool.submit(new callable());
        try {
            return AsyncDto.builder()
                    .status("success")
                    .result(true)
                    .message(result2.get())
                    .time(now)
                    .build();
        } catch (ExecutionException e) {
            throw new RuntimeException(now + "非同期処理中に問題発生", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(now + "←の時刻で割り込み処理がありました", e);
        }
    }

    // ScheduleThread
    public AsyncDto useScheduleThreadPool(AsyncRequest request, LocalDateTime now) {
        var threadPool = Executors.newScheduledThreadPool(2);
        threadPool.scheduleWithFixedDelay(() -> {log.info(String.valueOf(Thread.currentThread().getId()));},
                1, 1, TimeUnit.SECONDS);
        threadPool.scheduleWithFixedDelay(() -> {log.info(String.valueOf(Thread.currentThread().getId()));},
                1, 1, TimeUnit.SECONDS);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException("割り込み処理が発生", e);
        }
        threadPool.shutdown();

        return AsyncDto.builder()
                .status("success")
                .result(true)
                .time(now)
                .build();
    }

    // ThreadPoolTaskExecutorを利用する
    public AsyncDto useThreadPoolTask(AsyncRequest request, LocalDateTime now) {
        threadPoolTaskExecutor.execute(() -> {
            log.info(Thread.currentThread().getName() + Thread.currentThread().getId());
            try{
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException("割り込み", e);
            }
            log.info("処理1終了");
        });

        threadPoolTaskExecutor.execute(() -> {
            log.info(Thread.currentThread().getName() + Thread.currentThread().getId());
            try{
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException("割り込み", e);
            }
            log.info("処理2終了");
        });

        threadPoolTaskExecutor.execute(() -> {
            IntStream.iterate(0, a -> a+1).limit(10).forEach(b -> log.info(String.valueOf(b)));
            log.info("処理3終了");});

        return AsyncDto.builder()
                .status("success")
                .result(true)
                .time(now)
                .build();
    }

    // ThreadPoolTaskExecutorを@Asyncで利用する
    @Async("Thread1")
    public void useThreadPoolTaskAsync() {
        log.info("非同期start→" + Thread.currentThread().getId() + "、" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException("割り込み", e);
        }
        log.info("非同期finish→" + Thread.currentThread().getId() + "、"+ Thread.currentThread().getName());
    }

    public void useThreadPoolTaskAsyncTest() {
        useThreadPoolTaskTest.useThreadPoolTaskAsync();
        useThreadPoolTaskTest.useThreadPoolTaskAsync();
        useThreadPoolTaskTest.useThreadPoolTaskAsync();
    }

    // CompletableFutureを利用
    public void useCompletableFuture() {
        var use1 = CompletableFuture.supplyAsync(
                () -> {
                        try{
                            Thread.sleep(1000);
                        } catch(InterruptedException e) {
                            throw new RuntimeException("割り込み", e);
                        }
                        log.info("処理1終わり");
                        return "処理1";
                });

        var use2 = use1.thenRunAsync(new commonTask(), threadPoolTaskExecutor);

        var use3 = use1.thenApplyAsync(
                value -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException("割り込み", e);
                    }
                    log.info("処理終わり→" + Thread.currentThread().getId() + Thread.currentThread().getName());
                    return value + "+処理3";
                }, threadPoolTaskExecutor);

        use2.thenCombine(use3, (a,b) -> a + b)
                .thenAccept(log::info);
    }


    // 共通タスク定義クラス1
    private static class commonTask implements Runnable {
        @Override
        public void run(){
            log.info("共通タスク開始します");
            Optional<String> a = Optional.empty();
            Optional<String> b = Optional.of("abcde");
            Optional<String> c = Optional.ofNullable(null);
            a = b.map(String::toUpperCase);

            List<String> list = Stream.of(a,b,c).map(op -> op.orElse("置換" + Thread.currentThread().getId())).toList();
            Set<String> set = Stream.of(a,b,c).map(op -> op.orElse("置換" + Thread.currentThread().getId() + "e")).filter(fil -> fil.endsWith("e")).collect(Collectors.toSet());

            list.forEach(log::info);
            set.forEach(log::info);
            IntStream.rangeClosed(1, 10).forEach(d -> log.info(String.valueOf(d)));
            try{
            Thread.sleep(2000);
            } catch (InterruptedException e) {
            throw new RuntimeException("割り込み", e);
            }
            log.info(Thread.currentThread().getId() + Thread.currentThread().getName() + "← 終了します");
        }
    }

    @Data
    @Builder
    public static class AsyncDto {
        private String status;
        private boolean result;
        private String message;
        private LocalDateTime time;
    }
}
