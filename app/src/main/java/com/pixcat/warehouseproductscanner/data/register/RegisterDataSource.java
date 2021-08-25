package com.pixcat.warehouseproductscanner.data.register;

import android.util.Log;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.RetrofitServiceFactory;
import com.pixcat.warehouseproductscanner.data.auth.UserRoles;
import com.pixcat.warehouseproductscanner.data.model.UserDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Response;

public class RegisterDataSource {

    private static final String TAG = "RegisterDataSource";

    private static final List<String> requiredRoles = new ArrayList<>(Arrays.asList(
            UserRoles.READ_PRODUCTS,
            UserRoles.WRITE_PRODUCTS)
    );

    private final RegisterService registerService = RetrofitServiceFactory
            .create(RegisterService.class);

    public Result<Boolean> registerUser(String username, String password) {
        UserDto userDto = new UserDto(username, password, requiredRoles);
        try {
            Response<Void> registerResponse = registerService.registerUser(userDto).execute();
            if (registerResponse.isSuccessful()) {
                return Result.success(true);
            }
            Log.d(TAG, "RegisterService response " + registerResponse.code());
            return Result.failure(R.string.register_error_server);
        } catch (RuntimeException | IOException e) {
            Log.d(TAG, e.getMessage(), e);

            return Result.failure(R.string.register_error_unknown);
        }
    }
}
