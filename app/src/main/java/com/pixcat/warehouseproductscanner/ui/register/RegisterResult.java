package com.pixcat.warehouseproductscanner.ui.register;

import androidx.annotation.Nullable;

class RegisterResult {

    @Nullable
    private CreatedUserView success;
    @Nullable
    private Integer error;

    RegisterResult(@Nullable Integer error) {
        this.error = error;
    }

    RegisterResult(@Nullable CreatedUserView success) {
        this.success = success;
    }

    @Nullable
    CreatedUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
