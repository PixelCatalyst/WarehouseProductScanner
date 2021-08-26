package com.pixcat.warehouseproductscanner.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.auth.ActiveUserRepository;
import com.pixcat.warehouseproductscanner.data.auth.AuthDataSource;

public class MainNavActivity extends AppCompatActivity {

    ActiveUserRepository activeUserRepository = ActiveUserRepository.getInstance(new AuthDataSource());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        String welcome = getString(R.string.welcome) + activeUserRepository.getUser().getUsername();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT).show();
    }
}