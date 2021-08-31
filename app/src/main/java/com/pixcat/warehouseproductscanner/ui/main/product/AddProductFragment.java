package com.pixcat.warehouseproductscanner.ui.main.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixcat.warehouseproductscanner.R;

import java.util.ArrayList;

public class AddProductFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        RecyclerView barcodesRecycler = view.findViewById(R.id.barcodes_recycler);

        ArrayList<String> barcodes = new ArrayList<>();
        barcodes.add("110011");
        barcodes.add("220022");
        barcodes.add("330033");
        BarcodesAdapter barcodesAdapter = new BarcodesAdapter(barcodes);
        barcodesRecycler.setAdapter(barcodesAdapter);
        barcodesRecycler.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        barcodesRecycler.setNestedScrollingEnabled(false);

        return view;
    }
}
