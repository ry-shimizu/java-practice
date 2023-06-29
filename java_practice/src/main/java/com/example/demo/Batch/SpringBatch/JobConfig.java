package com.example.demo.Batch.SpringBatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Random;

import static com.example.demo.Batch.SpringBatch.Match.opponentList;

@Configuration
@EnableBatchProcessing
@Slf4j
public class JobConfig {
    @Bean
    public Job battleJob(
            JobRepository jobRepository
            ,@Qualifier("firstBattle") Step firstBattle
            ,@Qualifier("secondBattle") Step secondBattle) {
        return new JobBuilder("battleJob", jobRepository)
                .start(firstBattle)
                .next(secondBattle)
                .build();
    }

    @Bean("firstBattle")
    public Step firstBattle(
            JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, @Qualifier("firstTasklet") Tasklet firstTasklet) {
        log.info("firstBattle開始");
        return new StepBuilder("firstBattle", jobRepository)
                .tasklet(firstTasklet, platformTransactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean("firstTasklet")
    @StepScope
    public Tasklet firstTasklet(@Value("#{jobParameters['mainCharacter']}") String mainCharacter) {
        log.info("firstTasklet開始");
        var tasklet = new MethodInvokingTaskletAdapter();
        tasklet.setTargetObject(new Match());
        tasklet.setTargetMethod("battle");
        tasklet.setArguments(new Object[]{mainCharacter, opponentList[new Random().nextInt(2)]});
        return tasklet;
    }

    @Bean("secondBattle")
    public Step secondBattle(
            JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, @Qualifier("secondTasklet") Tasklet secondTasklet) {
        log.info("secondBattle開始");
        return new StepBuilder("secondBattle", jobRepository)
                .tasklet(secondTasklet, platformTransactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean("secondTasklet")
    @StepScope
    public Tasklet secondTasklet(@Value("#{jobParameters['mainCharacter']}") String mainCharacter) {
        log.info("secondTasklet開始");
        var tasklet = new MethodInvokingTaskletAdapter();
        tasklet.setTargetObject(new Match());
        tasklet.setTargetMethod("battle");
        tasklet.setArguments(new Object[]{mainCharacter, opponentList[new Random().nextInt(2)]});
        return tasklet;
    }
}
