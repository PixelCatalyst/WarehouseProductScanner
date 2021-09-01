package com.pixcat.warehouseproductscanner.ui.main.product;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.model.ActiveUser;
import com.pixcat.warehouseproductscanner.data.model.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AddProductFragment extends Fragment {

    private final ActiveUser activeUser;

    private final ProductDto.Builder productBuilder = new ProductDto.Builder();
    private ProductViewModel productViewModel;

    private EditText productIdEdit;
    private EditText descriptionEdit;
    private EditText heightEdit;
    private EditText widthEdit;
    private EditText lengthEdit;
    private EditText weightEdit;
    private RadioGroup tempRadioGroup;
    private BarcodesAdapter barcodesAdapter;

    public AddProductFragment(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        productIdEdit = view.findViewById(R.id.edit_productid);
        descriptionEdit = view.findViewById(R.id.edit_description);
        heightEdit = view.findViewById(R.id.edit_height);
        widthEdit = view.findViewById(R.id.edit_width);
        lengthEdit = view.findViewById(R.id.edit_length);
        weightEdit = view.findViewById(R.id.edit_weight);
        tempRadioGroup = view.findViewById(R.id.radio_temp);

        // TODO use camera to update product photo

        // TODO Initial select temp based on temp red from sensors
        tempRadioGroup.check(R.id.radio_temp_ambient);

        Button createProductButton = view.findViewById(R.id.button_create_product);
        createProductButton.setOnClickListener(v -> dispatchCreation());
        createProductButton.setEnabled(false);

        RecyclerView barcodesRecycler = view.findViewById(R.id.barcodes_recycler);

        // TODO handle barcode changes
        ArrayList<String> barcodes = new ArrayList<>();
        barcodes.add("110011");
        barcodes.add("220022");
        barcodes.add("330033");

        barcodesAdapter = new BarcodesAdapter(barcodes);
        barcodesRecycler.setAdapter(barcodesAdapter);
        barcodesRecycler.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        barcodesRecycler.setNestedScrollingEnabled(false);

        productViewModel = new ViewModelProvider(this, new ProductViewModelFactory(activeUser))
                .get(ProductViewModel.class);

        productViewModel.getProductFormState().observe(getViewLifecycleOwner(), productFormState -> {
            if (productFormState == null) {
                return;
            }
            createProductButton.setEnabled(productFormState.isDataValid());
            if (productFormState.getProductIdError() != null) {
                productIdEdit.setError(getString(productFormState.getProductIdError()));
            }
            if (productFormState.getHeightError() != null) {
                heightEdit.setError(getString(productFormState.getHeightError()));
            }
            if (productFormState.getWidthError() != null) {
                widthEdit.setError(getString(productFormState.getWidthError()));
            }
            if (productFormState.getLengthError() != null) {
                lengthEdit.setError(getString(productFormState.getLengthError()));
            }
            if (productFormState.getWeightError() != null) {
                weightEdit.setError(getString(productFormState.getWeightError()));
            }
        });
        productViewModel.getCreateProductResult().observe(getViewLifecycleOwner(), createProductResult -> {
            if (createProductResult == null) {
                return;
            }
            if (createProductResult.getError() != null) {
                Toast.makeText(
                        requireActivity().getApplicationContext(),
                        createProductResult.getError(),
                        Toast.LENGTH_LONG)
                        .show();
            }
            if (createProductResult.getSuccess() != null) {
                Toast.makeText(
                        requireActivity().getApplicationContext(),
                        R.string.product_created,
                        Toast.LENGTH_SHORT)
                        .show();
                // TODO launch "Show Product" Fragment
            }
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
                notifyFormUpdate();
            }
        };
        productIdEdit.addTextChangedListener(afterTextChangedListener);
        descriptionEdit.addTextChangedListener(afterTextChangedListener);
        heightEdit.addTextChangedListener(afterTextChangedListener);
        widthEdit.addTextChangedListener(afterTextChangedListener);
        lengthEdit.addTextChangedListener(afterTextChangedListener);
        weightEdit.addTextChangedListener(afterTextChangedListener);
        tempRadioGroup.setOnCheckedChangeListener((group, checkedId) -> notifyFormUpdate());

        return view;
    }

    private void dispatchCreation() {
        ProductDto product = assembleProductData();
        productViewModel.createProduct(product);
    }

    private void notifyFormUpdate() {
        ProductDto product = assembleProductData();
        productViewModel.productFormChanged(product);
    }

    private ProductDto assembleProductData() {
        return productBuilder
                .productId(productIdEdit.getText().toString())
                .description(descriptionEdit.getText().toString())
                .heightInMillimeters(mapMillimeters(heightEdit.getText().toString()))
                .widthInMillimeters(mapMillimeters(widthEdit.getText().toString()))
                .lengthInMillimeters(mapMillimeters(lengthEdit.getText().toString()))
                .weightInKilograms(mapKilograms(weightEdit.getText().toString()))
                .storageTemperature(mapCheckedRadioToTemperature(tempRadioGroup.getCheckedRadioButtonId()))
                .barcodes(barcodesAdapter.getBarcodes())
                .build();
    }

    private Integer mapMillimeters(String value) {
        if (value != null && !value.trim().isEmpty()) {
            return Integer.parseInt(value);
        }
        return null;
    }

    private BigDecimal mapKilograms(String value) {
        if (value != null && !value.trim().isEmpty()) {
            return new BigDecimal(value);
        }
        return null;
    }

    private String mapCheckedRadioToTemperature(int id) {
        if (id == R.id.radio_temp_ambient) {
            return "AMBIENT";
        } else {
            return "CHILL";
        }
    }
}
