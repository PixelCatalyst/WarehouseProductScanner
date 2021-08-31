package com.pixcat.warehouseproductscanner.ui.main.product;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pixcat.warehouseproductscanner.data.model.ActiveUser;
import com.pixcat.warehouseproductscanner.data.product.ProductDataSource;

public class ProductViewModelFactory implements ViewModelProvider.Factory {

    private final ActiveUser activeUser;

    public ProductViewModelFactory(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProductViewModel.class)) {
            return (T) new ProductViewModel(new ProductDataSource(activeUser.getAuthToken()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
