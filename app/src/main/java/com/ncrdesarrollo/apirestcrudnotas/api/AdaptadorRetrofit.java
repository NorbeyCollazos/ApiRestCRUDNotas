package com.ncrdesarrollo.apirestcrudnotas.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdaptadorRetrofit {

    Retrofit retrofit;

    public AdaptadorRetrofit() {
    }

    public Retrofit getAdapter(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://apirestcrudnotas.ncrdesarrollo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
