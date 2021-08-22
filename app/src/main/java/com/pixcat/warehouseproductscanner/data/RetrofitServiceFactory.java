package com.pixcat.warehouseproductscanner.data;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceFactory {

    public static final String API_BASE_URL = "http://10.0.2.2:8080";

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder()
                            .setLenient()
                            .create())
            );

    private static Retrofit retrofit = retrofitBuilder.build();

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
        if (!TextUtils.isEmpty(authToken)) {
            AuthInterceptor interceptor = new AuthInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                retrofitBuilder.client(httpClient.build());
                retrofit = retrofitBuilder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
