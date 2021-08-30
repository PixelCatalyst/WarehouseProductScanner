package com.pixcat.warehouseproductscanner.ui.main.search;

import androidx.annotation.Nullable;

import com.pixcat.warehouseproductscanner.data.model.ProductDto;

public class SearchResult {

    private final String searchedId;
    @Nullable
    private ProductDto success;
    @Nullable
    private Integer error;

    SearchResult(String searchedId, @Nullable Integer error) {
        this.searchedId = searchedId;
        this.error = error;
    }

    SearchResult(String searchedId, @Nullable ProductDto success) {
        this.searchedId = searchedId;
        this.success = success;
    }

    public String getSearchedId() {
        return searchedId;
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
