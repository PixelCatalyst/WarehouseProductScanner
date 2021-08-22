package com.pixcat.warehouseproductscanner.data;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AuthInterceptor implements Interceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final String authToken;

    public AuthInterceptor(String token) {
        this.authToken = token;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request authedRequest = originalRequest.newBuilder()
                .header(AUTHORIZATION_HEADER, authToken)
                .build();

        return chain.proceed(authedRequest);
    }
}