package com.example.warhome.mytablayour;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro {
    private static final String ROOT_URL = "https://agile-dawn-86874.herokuapp.com";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
