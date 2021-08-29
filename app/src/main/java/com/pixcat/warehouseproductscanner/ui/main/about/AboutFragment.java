package com.pixcat.warehouseproductscanner.ui.main.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.auth.UserRolesDataSource;
import com.pixcat.warehouseproductscanner.data.model.ActiveUser;

import java.util.List;

public class AboutFragment extends Fragment {

    private final ActiveUser activeUser;
    private final UserRolesDataSource userRolesDataSource;

    public AboutFragment(ActiveUser activeUser) {
        this.activeUser = activeUser;
        this.userRolesDataSource = new UserRolesDataSource(activeUser.getAuthToken());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        TextView helloBanner = view.findViewById(R.id.hello_banner);
        helloBanner.setText(getString(R.string.hello_banner, activeUser.getUsername()));

        ExpandableListView elv = view.findViewById(R.id.expandableListView);
        AboutListAdapter aboutListAdapter = new AboutListAdapter(requireActivity());
        elv.setAdapter(aboutListAdapter);

        new Thread(() -> {
            Result<List<String>> userRoles = userRolesDataSource.getRoles();
            if (userRoles.isSuccess()) {
                aboutListAdapter.updateRolesList(userRoles.getData());
            }
        }).start();

        return view;
    }
}
