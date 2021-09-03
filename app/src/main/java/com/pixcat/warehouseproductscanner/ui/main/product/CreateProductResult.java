package com.pixcat.warehouseproductscanner.ui.main.product;

import androidx.annotation.Nullable;

import com.pixcat.warehouseproductscanner.data.model.ProductDto;

public class CreateProductResult {

    @Nullable
    private ProductDto success;

    private final boolean imageSuccess;

    @Nullable
    private Integer error;

    CreateProductResult(@Nullable Integer error) {
        this.error = error;
        this.imageSuccess = false;
    }

    CreateProductResult(@Nullable ProductDto success, boolean imageSuccess) {
        this.success = success;
        this.imageSuccess = imageSuccess;
    }

    @Nullable
    ProductDto getSuccess() {
        return success;
    }

    public boolean isImageSuccess() {
        return imageSuccess;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
