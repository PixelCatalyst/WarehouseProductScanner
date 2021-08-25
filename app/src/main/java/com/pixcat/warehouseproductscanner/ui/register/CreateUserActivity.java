package com.pixcat.warehouseproductscanner.ui.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.databinding.ActivityCreateUserBinding;
import com.pixcat.warehouseproductscanner.ui.SharedExtras;
import com.pixcat.warehouseproductscanner.ui.auth.LoginActivity;

public class CreateUserActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;

    private String registeredUsername = null;
    private String savedUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addBackButtonCallback();

        ActivityCreateUserBinding binding = ActivityCreateUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password1;
        final EditText repeatPasswordEditText = binding.password2;
        final ProgressBar loadingProgressBar = binding.loading;
        final Button createAccountButton = binding.createAccount;

        savedUsername = getIntent().getStringExtra(SharedExtras.ENTERED_USERNAME);
        if (savedUsername != null && !savedUsername.isEmpty()) {
            usernameEditText.setText(savedUsername);
        }

        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        registerViewModel.getRegisterFormState().observe(this, registerFormState -> {
            if (registerFormState != null) {
                createAccountButton.setEnabled(registerFormState.isDataValid());
                usernameEditText.setError(getStringOrNull(registerFormState.getUsernameError()));
                passwordEditText.setError(getStringOrNull(registerFormState.getPasswordError()));
                repeatPasswordEditText.setError(getStringOrNull(registerFormState.getRepeatPasswordError()));
            }
        });

        registerViewModel.getRegisterResult().observe(this, registerResult -> {
            if (registerResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (registerResult.getError() != null) {
                showRegisterFailed(registerResult.getError());
            }
            if (registerResult.getSuccess() != null) {
                updateOnSuccessfulRegister(registerResult.getSuccess());
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
                registerViewModel.registerFormChanged(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        repeatPasswordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        repeatPasswordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerViewModel.register(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        createAccountButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            registerViewModel.register(
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });
    }

    private void addBackButtonCallback() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backToLoginActivity();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private String getStringOrNull(@StringRes Integer stringRes) {
        if (stringRes == null) {
            return null;
        }
        return getString(stringRes);
    }

    private void updateOnSuccessfulRegister(CreatedUserView model) {
        registeredUsername = model.getUsername();

        // TODO Send push notification instead of Toast
        String welcome = getString(R.string.welcome) + model.getUsername();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT).show();

        backToLoginActivity();
    }

    private void backToLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        if (registeredUsername != null) {
            intent.putExtra(SharedExtras.REGISTERED_USERNAME, registeredUsername);
        } else if (savedUsername != null) {
            intent.putExtra(SharedExtras.ENTERED_USERNAME, savedUsername);
        }
        startActivity(intent);
        finish();
    }

    private void showRegisterFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_LONG).show();
    }
}
