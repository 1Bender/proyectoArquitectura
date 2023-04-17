package com.example.arquitecturaandroid.bussines;

import com.example.arquitecturaandroid.DTO.Estudiante;
import com.example.arquitecturaandroid.DTO.Notas;
import com.example.arquitecturaandroid.DTO.Usuario;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiController {

    @POST("students")
    Call <List<Usuario>> getAnswers(@Query("id") String id);

    @POST("add")
    Call <List<Notas>> setNotes(@Query("id") String id, @Query("notaMat") Double mates, @Query("notaSoci")
    Double social, @Query("notaLeng") Double lengua, @Query("notaDib") Double dibujo);

    @GET("student")
    Call <List<Estudiante>> getIdBoletin(@Query("neme") String nombre);
}
