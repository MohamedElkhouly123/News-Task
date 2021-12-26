package com.example.newstaskapp.view.main.data.api;


import com.example.newstaskapp.view.main.data.models.getNewsListResponce.GetNewsListResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("everything")
    Call<GetNewsListResponce> getNewsList(
            @Query("q") String q ,
            @Query("sortBy") String sortBy ,
            @Query("from") String from ,
//            @Query("sources") String sources ,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<GetNewsListResponce> getTopNewsList(
            @Query("q") String q ,
            @Query("sortBy") String sortBy ,
            @Query("from") String from ,
            @Query("country") String country ,
            @Query("category") String category ,
            @Query("apiKey") String apiKey
    );
}
