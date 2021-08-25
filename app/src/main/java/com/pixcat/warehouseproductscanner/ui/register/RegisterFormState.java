package com.pixcat.warehouseproductscanner.ui.register;

import androidx.annotation.Nullable;

class RegisterFormState {

    private final Integer usernameError;
    private final Integer passwordError;
    private final Integer repeatPasswordError;
    private final boolean isDataValid;

    RegisterFormState(
            @Nullable Integer usernameError,
            @Nullable Integer passwordError,
            @Nullable Integer repeatPasswordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.repeatPasswordError = repeatPasswordError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.repeatPasswordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getRepeatPasswordError() {
        return repeatPasswordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
