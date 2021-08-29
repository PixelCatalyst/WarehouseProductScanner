package com.pixcat.warehouseproductscanner.data.auth;

import android.util.Log;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.RetrofitServiceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class UserRolesDataSource {

    private static final String TAG = "UserRolesDataSource";

    private final AuthService authService;

    public UserRolesDataSource(String authToken) {
        authService = RetrofitServiceFactory.create(AuthService.class, authToken);
    }

    public Result<List<String>> getRoles() {
        Response<List<String>> rolesResponse;
        try {
            rolesResponse = authService.getUserAuth().execute();
        } catch (RuntimeException | IOException e) {
            Log.d(TAG, e.getMessage(), e);

            return Result.failure(R.string.request_failed);
        }
        if (rolesResponse.isSuccessful()) {
            return Result.success(
                    rolesResponse.body() != null
                            ? rolesResponse.body()
                            : new ArrayList<>()
            );
        }
        return Result.failure(R.string.request_failed);
    }
}
