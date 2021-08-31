package com.pixcat.warehouseproductscanner.ui.main.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixcat.warehouseproductscanner.R;

import java.util.ArrayList;
import java.util.List;

public class BarcodesAdapter extends RecyclerView.Adapter<BarcodesAdapter.ViewHolder> {

    private final List<String> barcodes;

    public BarcodesAdapter(List<String> barcodes) {
        this.barcodes = new ArrayList<>(barcodes);
    }

    @NonNull
    @Override
    public BarcodesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View barcodeView = inflater.inflate(R.layout.barcode_item, parent, false);

        return new ViewHolder(barcodeView);
    }

    @Override
    public void onBindViewHolder(BarcodesAdapter.ViewHolder holder, int position) {
        String barcode = barcodes.get(position);

        EditText barcodeEdit = holder.barcodeEdit;
        barcodeEdit.setText(barcode);

//        Button button = holder.deleteButton;
    }

    @Override
    public int getItemCount() {
        return barcodes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public EditText barcodeEdit;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            barcodeEdit = itemView.findViewById(R.id.barcode_value);
            deleteButton = itemView.findViewById(R.id.delete_barcode_button);
        }
    }
}
