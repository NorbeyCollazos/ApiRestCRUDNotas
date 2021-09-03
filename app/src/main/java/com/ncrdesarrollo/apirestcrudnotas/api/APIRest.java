package com.ncrdesarrollo.apirestcrudnotas.api;

import com.ncrdesarrollo.apirestcrudnotas.models.Notas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIRest {

    @GET("notas")
    Call<List<Notas>> obtenerNotas();

    @GET("notas")
    Call<List<Notas>> obtenerNota(
            @Query("id") String id
    );

    @POST("notas")
    Call<Void> agregarNota(
            @Body Notas notas
    );

    @PUT("notas")
    Call<Void> editarNota(
            @Body Notas notas
    );

    @DELETE("notas")
    Call<Void> eliminarNota(
            @Header("id") String id
    );
}
