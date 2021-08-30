package com.pixcat.warehouseproductscanner.ui.main.search;

import androidx.annotation.Nullable;

public class SearchFormState {

    private final Integer idError;
    private final boolean isDataValid;

    SearchFormState(@Nullable Integer idError) {
        this.idError = idError;
        this.isDataValid = false;
    }

    SearchFormState(boolean isDataValid) {
        this.idError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getIdError() {
        return idError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
