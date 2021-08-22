package com.pixcat.warehouseproductscanner.ui.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pixcat.warehouseproductscanner.data.auth.ActiveUserRepository;
import com.pixcat.warehouseproductscanner.data.auth.AuthDataSource;

public class AuthViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel(ActiveUserRepository.getInstance(new AuthDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
