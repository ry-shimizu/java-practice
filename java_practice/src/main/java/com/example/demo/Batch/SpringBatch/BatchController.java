package com.example.demo.Batch.SpringBatch;

import com.example.demo.Exception.ChildExampleException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BatchController {
    private final JobLauncher jobLauncher;
    private final Job battleJob;

    @GetMapping("/batchLaunch/{mainCharacter}")
    public ResponseEntity<String> batchLaunch(@PathVariable("mainCharacter") String mainCharacter) {
        try{
            jobLauncher.run(battleJob,
                    new JobParametersBuilder().addString("mainCharacter", mainCharacter).toJobParameters());
        return ResponseEntity.ok("ok");
        } catch (JobExecutionException e) {
            throw new ChildExampleException("jobの実行に失敗しました", 0, e);
        }
   }

}
