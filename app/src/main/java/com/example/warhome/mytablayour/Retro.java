package com.example.warhome.mytablayour;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro {
    private static final String ROOT_URL = "https://agile-dawn-86874.herokuapp.com";

    private static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
