package com.pixcat.warehouseproductscanner.ui.main.product;

import static java.util.stream.Collectors.toList;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixcat.warehouseproductscanner.R;

import java.util.List;

public class BarcodesAdapter extends RecyclerView.Adapter<BarcodesAdapter.ViewHolder> {

    private final List<Barcode> barcodes;

    public BarcodesAdapter(List<String> barcodes) {
        this.barcodes = barcodes.stream()
                .map(Barcode::new)
                .collect(toList());
    }

    public List<String> getBarcodes() {
        return barcodes.stream()
                .map(barcode -> barcode.value.trim())
                .filter(e -> !e.isEmpty())
                .distinct()
                .collect(toList());
    }

    public void addBarcode() {
        barcodes.add(new Barcode(""));
        notifyItemInserted(barcodes.size() - 1);
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
        EditText barcodeEdit = holder.barcodeEdit;
        barcodeEdit.setText(barcodes.get(position).value);
        barcodeEdit.addTextChangedListener(new TextWatcher() {
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
                barcodes.get(holder.getAdapterPosition()).value = barcodeEdit.getText().toString();
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            int index = holder.getAdapterPosition();
            barcodes.remove(index);
            notifyItemRemoved(index);
        });
    }

    private static class Barcode {
        String value;

        public Barcode(String value) {
            this.value = value;
        }
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
