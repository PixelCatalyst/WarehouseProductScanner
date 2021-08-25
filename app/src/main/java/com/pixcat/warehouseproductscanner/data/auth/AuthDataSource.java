package com.pixcat.warehouseproductscanner.data.auth;

import android.util.Log;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.RetrofitServiceFactory;
import com.pixcat.warehouseproductscanner.data.model.ActiveUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Response;

public class AuthDataSource {

    private static final String TAG = "AuthDataSource";

    private static final List<String> requiredRoles = new ArrayList<>(Arrays.asList(
            UserRoles.READ_PRODUCTS,
            UserRoles.WRITE_PRODUCTS)
    );

    public Result<ActiveUser> login(String username, String password) {
        Response<List<String>> authResponse;
        try {
            AuthService authService = RetrofitServiceFactory.create(AuthService.class, username, password);
            authResponse = authService.getUserAuth().execute();
        } catch (RuntimeException | IOException e) {
            Log.d(TAG, e.getMessage(), e);

            return Result.failure(R.string.login_error_unknown);
        }
        return validateResponse(ActiveUser.of(username, password), authResponse);
    }

    private Result<ActiveUser> validateResponse(ActiveUser user, Response<List<String>> response) {
        if (response.code() == 403 || response.code() == 401) {
            return Result.failure(R.string.invalid_credentials);
        } else if (response.code() > 400) {
            return Result.failure(R.string.login_error_server);
        } else if (response.body() == null || !response.body().containsAll(requiredRoles)) {
            return Result.failure(R.string.login_error_forbidden);
        }
        return Result.success(user);
    }
}
