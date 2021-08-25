package com.pixcat.warehouseproductscanner.ui.register;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pixcat.warehouseproductscanner.data.auth.ActiveUserRepository;
import com.pixcat.warehouseproductscanner.data.auth.AuthDataSource;
import com.pixcat.warehouseproductscanner.data.register.RegisterDataSource;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(new RegisterDataSource());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
