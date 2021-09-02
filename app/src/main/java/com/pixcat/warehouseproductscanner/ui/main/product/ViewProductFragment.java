package com.pixcat.warehouseproductscanner.ui.main.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.model.ProductDto;

import java.math.BigDecimal;
import java.util.Locale;

public class ViewProductFragment extends Fragment {

    private final ProductDto product;

    public ViewProductFragment(ProductDto product) {
        this.product = product;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_product, container, false);

        TextView productIdView = view.findViewById(R.id.static_productid);
        TextView descriptionView = view.findViewById(R.id.static_description);
        TextView heightView = view.findViewById(R.id.static_height);
        TextView widthView = view.findViewById(R.id.static_width);
        TextView lengthView = view.findViewById(R.id.static_length);
        TextView weightView = view.findViewById(R.id.static_weight);
        RadioGroup tempRadioGroup = view.findViewById(R.id.radio_temp);

        productIdView.setText(product.getProductId());
        descriptionView.setText(product.getDescription());
        heightView.setText(formatMeasure(product.getHeightInMillimeters()));
        widthView.setText(formatMeasure(product.getWidthInMillimeters()));
        lengthView.setText(formatMeasure(product.getLengthInMillimeters()));
        weightView.setText(formatWeight(product.getWeightInKilograms()));
        String temperature = product.getStorageTemperature();

        if (temperature.equalsIgnoreCase("AMBIENT")) {
            tempRadioGroup.check(R.id.static_radio_temp_ambient);
        } else if (temperature.equalsIgnoreCase("CHILL")) {
            tempRadioGroup.check(R.id.static_radio_temp_chilled);
        }

        RecyclerView barcodesRecycler = view.findViewById(R.id.static_barcodes_recycler);
        BarcodesAdapter barcodesAdapter = new BarcodesAdapter(product.getBarcodes(), false);
        barcodesRecycler.setAdapter(barcodesAdapter);
        barcodesRecycler.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        barcodesRecycler.setNestedScrollingEnabled(false);

        return view;
    }

    private String formatMeasure(Integer measure) {
        return String.format(Locale.getDefault(), "%d %s", measure, getString(R.string.measure_unit));
    }

    private String formatWeight(BigDecimal weight) {
        return String.format(Locale.getDefault(), "%.3f %s", weight, getString(R.string.weight_unit));
    }
}
