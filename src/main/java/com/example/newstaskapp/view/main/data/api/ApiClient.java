package com.example.newstaskapp.view.main.data.api;



import static com.example.newstaskapp.view.main.views.activities.BaseActivity.BASE_URL2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    public static final String BASE_URL = BASE_URL2;

    public static Retrofit retrofit = null;
    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private static ApiServices retrofitCreat;

    public static ApiServices getApiClient(){

        if(retrofit == null){
            try {
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
//                    .callTimeout(2, TimeUnit.MINUTES)
                        .connectTimeout(200, TimeUnit.SECONDS)
                        .readTimeout(150, TimeUnit.SECONDS)
                        .writeTimeout(150, TimeUnit.SECONDS);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(httpClient
//                                .addInterceptor(new Interceptor() {
//                            @Override
//                            public Response intercept(Chain chain) throws IOException {
//                                final Request original = chain.request();
//
//                                final Request authorized = original.newBuilder()
//                                        .addHeader("Cookie", BASE_cook)
//                                        .build();
//                                return chain.proceed(authorized);
//                            }
//                        })
                                .build())
                        ;

                retrofit = builder.build();
              retrofitCreat= retrofit
                      .create(ApiServices.class);
            }catch (Exception e){
            }
        }
        return retrofitCreat;
    }
}
