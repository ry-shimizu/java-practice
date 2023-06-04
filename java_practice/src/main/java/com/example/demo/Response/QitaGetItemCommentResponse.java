package com.example.demo.Response;

import com.example.demo.Service.QitaGetItemCommentService.QitaGetItemCommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QitaGetItemCommentResponse {
    private QitaGetItemCommentDto[] dto;
}
