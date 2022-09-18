package com.example.newstaskapp.view.main.data.api

import com.example.newstaskapp.view.main.views.activities.BaseActivityKotlin
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClientKotlin {
    var retrofit: Retrofit? = null
    val BASE_URL = BaseActivityKotlin.BASE_URL2
    var gson = GsonBuilder()
        .setLenient()
        .create()
    private var retrofitCreat: ApiServicesKotlin? = null
    val apiClient: ApiServicesKotlin?
        get() {
            if (retrofit == null) {
                try {
                    val httpClient: OkHttpClient.Builder =
                        OkHttpClient.Builder() //                    .callTimeout(2, TimeUnit.MINUTES)
                            .connectTimeout(200, TimeUnit.SECONDS)
                            .readTimeout(150, TimeUnit.SECONDS)
                            .writeTimeout(150, TimeUnit.SECONDS)
                    val builder = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(
                            httpClient.build()
                        )
                    retrofit = builder.build()
                    retrofitCreat = retrofit!!
                        .create(ApiServicesKotlin::class.java)
                } catch (e: Exception) {
                }
            }
            return retrofitCreat
        }
}