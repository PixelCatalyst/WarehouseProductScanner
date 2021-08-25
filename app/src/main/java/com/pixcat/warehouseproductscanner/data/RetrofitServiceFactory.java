package com.pixcat.warehouseproductscanner.data;

import android.text.TextUtils;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceFactory {

    public static final String API_BASE_URL = "http://10.0.2.2:8080";

    public static <S> S create(Class<S> serviceClass) {
        return create(serviceClass, null, null);
    }

    public static <S> S create(Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return create(serviceClass, authToken);
        }

        return create(serviceClass, null);
    }

    public static <S> S create(Class<S> serviceClass, final String authToken) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (!TextUtils.isEmpty(authToken)) {
            httpClientBuilder.addInterceptor(new AuthInterceptor(authToken));
        }

        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build()
                .create(serviceClass);
    }
}
