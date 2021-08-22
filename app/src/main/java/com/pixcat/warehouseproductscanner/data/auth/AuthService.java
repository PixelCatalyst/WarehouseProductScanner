package com.pixcat.warehouseproductscanner.data.auth;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthService {

    @GET("/auth")
    Call<List<String>> getUserAuth();
}
