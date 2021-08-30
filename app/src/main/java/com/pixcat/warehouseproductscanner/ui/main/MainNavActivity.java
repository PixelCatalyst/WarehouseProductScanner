package com.pixcat.warehouseproductscanner.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.auth.ActiveUserRepository;
import com.pixcat.warehouseproductscanner.data.auth.AuthDataSource;
import com.pixcat.warehouseproductscanner.ui.main.about.AboutFragment;
import com.pixcat.warehouseproductscanner.ui.main.product.AddProductFragment;
import com.pixcat.warehouseproductscanner.ui.main.search.SearchFragment;

public class MainNavActivity extends AppCompatActivity {

    ActiveUserRepository activeUserRepository = ActiveUserRepository.getInstance(new AuthDataSource());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AboutFragment(activeUserRepository.getUser()))
                    .commit();
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.nav_about) {
            selectedFragment = new AboutFragment(activeUserRepository.getUser());
        } else if (itemId == R.id.nav_add_product) {
            selectedFragment = new AddProductFragment();
        } else if (itemId == R.id.nav_search) {
            selectedFragment = new SearchFragment(activeUserRepository.getUser());
        }
        if (selectedFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
        return true;
    };
}