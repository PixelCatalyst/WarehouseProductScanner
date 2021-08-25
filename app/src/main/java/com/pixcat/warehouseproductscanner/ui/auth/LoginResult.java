package com.pixcat.warehouseproductscanner.ui.auth;

import androidx.annotation.Nullable;

import com.pixcat.warehouseproductscanner.data.model.ActiveUser;

class LoginResult {

    @Nullable
    private ActiveUser success;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable ActiveUser success) {
        this.success = success;
    }

    @Nullable
    ActiveUser getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
