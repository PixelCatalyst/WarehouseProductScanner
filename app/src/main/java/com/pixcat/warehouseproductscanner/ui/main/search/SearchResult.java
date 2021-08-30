package com.pixcat.warehouseproductscanner.ui.main.search;

import androidx.annotation.Nullable;

import com.pixcat.warehouseproductscanner.data.model.ProductDto;

public class SearchResult {

    @Nullable
    private ProductDto success;
    @Nullable
    private Integer error;

    SearchResult(@Nullable Integer error) {
        this.error = error;
    }

    SearchResult(@Nullable ProductDto success) {
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
