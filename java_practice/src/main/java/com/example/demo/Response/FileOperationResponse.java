package com.example.demo.Response;

import com.example.demo.Service.FileOperationService.FileOperationDto;
import lombok.Data;

@Data
public class FileOperationResponse {

    public FileOperationResponse(FileOperationDto dto) {
        content = dto.getContent();
    }

    private String content;
}
