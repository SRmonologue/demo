package com.ohosure.smart.net;


import com.ohosure.smart.core.Const;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by lee on 2017/11/22.
 */

public class RetrofitUtil {
    private static RetrofitApi httpApi;


    public static RetrofitApi createHttpApiInstance() {

        synchronized (RetrofitUtil.class) {
            if (httpApi == null) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(httpLoggingInterceptor)
                        .build();
                httpApi = new Retrofit
                        .Builder()
                        .client(okHttpClient)
                        .baseUrl(Const.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(RetrofitApi.class);
            }

        }

        return httpApi;
    }


}
