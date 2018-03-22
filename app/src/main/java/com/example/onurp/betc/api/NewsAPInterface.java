package com.example.onurp.betc.api;

import com.example.onurp.betc.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by onurp on 20.03.2018.
 */

public interface NewsAPInterface {
    String BASE_URL="https://newsapi.org/";


    @GET("v2/top-headlines?sources=crypto-coins-news")
    Call<News> getNews(@Query("apiKey") String apiKey);
}
