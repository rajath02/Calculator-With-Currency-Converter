package com.example.calculator.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("/v6/3908ba002b9de0562e22ee96/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}

