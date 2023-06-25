package com.example.demo.Batch.SpringBatch;

import com.example.demo.Exception.ExampleException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Slf4j
public class Match {

    public static String[] opponentList = {"shimizu", "sugahara", "suzuki"};
    private static final Set<String> record = new HashSet<>();



    public void battle(String mainCharacter, String opponentCharacter) {
        if (record.contains(mainCharacter)) {
            log.info(String.format("success: %s は %s と戦って勝利した", mainCharacter, opponentCharacter));
        } else {
            log.info(String.format("failed: %s は %s と戦って敗北した", mainCharacter, opponentCharacter));
            record.add(mainCharacter);
            throw new ExampleException("失敗しました", 0);
        }
    }


}
