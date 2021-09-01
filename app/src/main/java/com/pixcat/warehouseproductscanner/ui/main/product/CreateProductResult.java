package com.pixcat.warehouseproductscanner.ui.main.product;

import androidx.annotation.Nullable;

import com.pixcat.warehouseproductscanner.data.model.ProductDto;

public class CreateProductResult {

    @Nullable
    private ProductDto success;
    @Nullable
    private Integer error;

    CreateProductResult(@Nullable Integer error) {
        this.error = error;
    }

    CreateProductResult(@Nullable ProductDto success) {
        this.success = success;
    }

    @Nullable
    ProductDto getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
