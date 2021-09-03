package com.pixcat.warehouseproductscanner.ui.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pixcat.warehouseproductscanner.data.model.ActiveUser;
import com.pixcat.warehouseproductscanner.databinding.ActivityLoginBinding;
import com.pixcat.warehouseproductscanner.ui.SharedExtra;
import com.pixcat.warehouseproductscanner.ui.SharedPrefId;
import com.pixcat.warehouseproductscanner.ui.camera.CameraWrapper;
import com.pixcat.warehouseproductscanner.ui.main.MainNavActivity;
import com.pixcat.warehouseproductscanner.ui.register.CreateUserActivity;

public class LoginActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button registerButton = binding.register;
        final ProgressBar loadingProgressBar = binding.loading;

        String registeredUsername = getIntent().getStringExtra(SharedExtra.REGISTERED_USERNAME);
        String savedUsername = getIntent().getStringExtra(SharedExtra.ENTERED_USERNAME);
        if (registeredUsername != null) {
            usernameEditText.setText(registeredUsername);
        } else if (savedUsername != null) {
            usernameEditText.setText(savedUsername);
        } else {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            String lastUsedUsername = sharedPref.getString(SharedPrefId.LAST_USED_USERNAME, "");
            usernameEditText.setText(lastUsedUsername);
        }

        authViewModel = new ViewModelProvider(this, new AuthViewModelFactory())
                .get(AuthViewModel.class);

        authViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        authViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                registerButton.setEnabled(true);
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateOnSuccessfulLogin(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                authViewModel.loginFormChanged(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                authViewModel.login(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            registerButton.setEnabled(false);
            loadingProgressBar.setVisibility(View.VISIBLE);
            authViewModel.login(
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateUserActivity.class);
            intent.putExtra(SharedExtra.ENTERED_USERNAME, usernameEditText.getText().toString());
            startActivity(intent);
            finish();
        });

        CameraWrapper.getInstance().initCamera(this);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CameraWrapper.getInstance().onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void updateOnSuccessfulLogin(ActiveUser model) {
        rememberSuccessfulLogin(model.getUsername());

        Intent intent = new Intent(this, MainNavActivity.class);
        startActivity(intent);
    }

    private void rememberSuccessfulLogin(String username) {
        SharedPreferences.Editor sharedPrefEdit = getPreferences(Context.MODE_PRIVATE).edit();
        sharedPrefEdit.putString(SharedPrefId.LAST_USED_USERNAME, username);
        sharedPrefEdit.apply();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_LONG).show();
    }
}
