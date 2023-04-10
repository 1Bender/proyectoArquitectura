package com.example.arquitecturaandroid.bussines;

import com.example.arquitecturaandroid.DTO.Usuario;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiController {

    @POST("students")
    Call <List<Usuario>> getAnswers(@Query("id") String id);
}
