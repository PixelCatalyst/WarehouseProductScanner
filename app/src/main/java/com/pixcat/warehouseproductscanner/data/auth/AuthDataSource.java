package com.pixcat.warehouseproductscanner.data.auth;

import com.pixcat.warehouseproductscanner.R;
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
                return Result.failure(R.string.wrong_credentials);
            } else if (response.code() > 400) {
                return Result.failure(R.string.login_error_server);
            }
            return Result.success(user);
        } catch (RuntimeException | IOException e) {
            return Result.failure(R.string.login_error_unknown);
        }
    }
}
