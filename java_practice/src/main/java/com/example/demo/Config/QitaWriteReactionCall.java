package com.example.demo.Config;

import com.example.demo.Request.RequestSorce.QitaWriteReactionsResource;
import com.example.demo.Service.QitaWriteReactionService;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QitaWriteReactionCall {
    @POST("/api/v2/items/{itemId}/comments")
    Call<QitaWriteReactionService.QitaWriteReactionsDto> postData(
            @Header("Authorization") String token,
            @Path("itemId") String itemId,
            @Body QitaWriteReactionsResource resource);
}
