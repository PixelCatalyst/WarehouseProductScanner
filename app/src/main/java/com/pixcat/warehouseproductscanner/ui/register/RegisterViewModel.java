package com.pixcat.warehouseproductscanner.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.register.RegisterDataSource;

public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private final MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private final RegisterDataSource registerDataSource;

    RegisterViewModel(RegisterDataSource registerDataSource) {
        this.registerDataSource = registerDataSource;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String username, String password) {
        new Thread(() -> {
            Result<Boolean> result = registerDataSource.registerUser(username, password);

            if (result.isSuccess()) {
                registerResult.postValue(new RegisterResult(new CreatedUserView(username)));
            } else {
                registerResult.postValue(new RegisterResult(result.getError()));
            }
        }).start();
    }

    public void registerFormChanged(String username, String password, String repeatPassword) {
        Integer usernameError = null;
        Integer passwordError = null;
        Integer repeatPasswordError = null;
        if (!isUserNameValid(username)) {
            usernameError = R.string.invalid_username;
        }
        if (!isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        }
        if (!isRepeatedPasswordMatching(password, repeatPassword)) {
            repeatPasswordError = R.string.invalid_password_match;
        }

        if (usernameError == null && passwordError == null && repeatPasswordError == null) {
            registerFormState.setValue(new RegisterFormState(true));
        } else {
            registerFormState.setValue(new RegisterFormState(usernameError, passwordError, repeatPasswordError));
        }
    }

    private boolean isUserNameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }

    private boolean isRepeatedPasswordMatching(String password, String repeatPassword) {
        if (isPasswordValid(password) && isPasswordValid(repeatPassword)) {
            return password.trim().equals(repeatPassword.trim());
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        return password != null && !password.trim().isEmpty();
    }
}
