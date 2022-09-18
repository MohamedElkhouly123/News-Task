package com.example.newstaskapp.view.main.data.api

import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.GetNewsListResponceK
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicesKotlin {
    @GET("everything")
    fun getNewsList(
        @Query("q") q: String?,
        @Query("sortBy") sortBy: String?,
        @Query("from") from: String?,  //            @Query("sources") String sources ,
        @Query("apiKey") apiKey: String?
    ): Call<GetNewsListResponceK?>?

    @GET("top-headlines")
    fun getTopNewsList(
        @Query("q") q: String?,
        @Query("sortBy") sortBy: String?,
        @Query("from") from: String?,
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<GetNewsListResponceK?>?
}