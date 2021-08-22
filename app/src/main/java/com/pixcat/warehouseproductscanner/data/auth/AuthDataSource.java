package com.pixcat.warehouseproductscanner.data.auth;

import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.RetrofitServiceFactory;
import com.pixcat.warehouseproductscanner.data.model.ActiveUser;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class AuthDataSource {

    public Result<ActiveUser> login(String username, String password) {

        try {
            AuthService authService = RetrofitServiceFactory.create(AuthService.class, username, password);
            ActiveUser user = ActiveUser.of(username, password);
            Response<List<String>> response = authService.getUserAuth().execute();

            if (response.code() == 403 || response.code() == 401) {
                return new Result.Error(new RuntimeException("Wrong credentials"));
            } else if (response.code() > 400) {
                return new Result.Error(new RuntimeException("Error logging in (" + response.code() + ")"));
            }
            return new Result.Success<>(user);
        } catch (RuntimeException | IOException e) {
            return new Result.Error(new IOException(e.getMessage(), e.getCause()));
        }
    }
}
