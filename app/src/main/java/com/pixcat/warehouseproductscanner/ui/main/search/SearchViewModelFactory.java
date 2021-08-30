package com.pixcat.warehouseproductscanner.ui.main.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pixcat.warehouseproductscanner.data.model.ActiveUser;
import com.pixcat.warehouseproductscanner.data.product.ProductDataSource;

public class SearchViewModelFactory implements ViewModelProvider.Factory {

    private final ActiveUser activeUser;

    public SearchViewModelFactory(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(new ProductDataSource(activeUser.getAuthToken()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
