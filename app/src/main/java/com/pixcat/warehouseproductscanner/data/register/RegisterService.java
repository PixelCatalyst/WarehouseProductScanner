package com.pixcat.warehouseproductscanner.data.register;

import com.pixcat.warehouseproductscanner.data.model.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {

    @POST("/register")
    Call<Void> registerUser(@Body UserDto userDto);
}
