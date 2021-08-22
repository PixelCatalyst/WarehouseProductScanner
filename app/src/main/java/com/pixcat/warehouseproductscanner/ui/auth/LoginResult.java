package com.pixcat.warehouseproductscanner.ui.auth;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {

    @Nullable
    private ActiveUserView success;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable ActiveUserView success) {
        this.success = success;
    }

    @Nullable
    ActiveUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
