package com.spectra.fieldforce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientRetrofit {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    Gson gson = new GsonBuilder().create();
                    RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    int REQUEST_TIMEOUT = 2*60;
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .addNetworkInterceptor(httpLoggingInterceptor)
                            .connectTimeout(2, TimeUnit.MINUTES)
                            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS).build();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.PROD_BASE_URL1)
                            .client(okHttpClient)
                            .addCallAdapterFactory(rxAdapter)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
        }
        return retrofit;
    }


}
