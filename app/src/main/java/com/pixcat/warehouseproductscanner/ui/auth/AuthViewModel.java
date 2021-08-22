package com.pixcat.warehouseproductscanner.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.auth.ActiveUserRepository;
import com.pixcat.warehouseproductscanner.data.model.ActiveUser;

public class AuthViewModel extends ViewModel {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final ActiveUserRepository activeUserRepository;

    AuthViewModel(ActiveUserRepository activeUserRepository) {
        this.activeUserRepository = activeUserRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        new Thread(() -> {
            Result<ActiveUser> result = activeUserRepository.login(username, password);

            if (result instanceof Result.Success) {
                ActiveUser data = ((Result.Success<ActiveUser>) result).getData();
                loginResult.postValue(new LoginResult(new LoggedInUserView(data.getUsername())));
            } else {
                loginResult.postValue(new LoginResult(R.string.login_failed));
            }
        }).start();
    }

    public void loginFormChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return !username.trim().isEmpty();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
