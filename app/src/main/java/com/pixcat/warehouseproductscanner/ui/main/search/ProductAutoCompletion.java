package com.pixcat.warehouseproductscanner.ui.main.search;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.pixcat.warehouseproductscanner.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProductAutoCompletion {

    private static final String TAG = "ProductAutoCompletion";

    private static final AtomicBoolean isUpdateInProgress = new AtomicBoolean(false);

    private final ArrayList<String> suggestions = new ArrayList<>();
    private final Context context;
    private final ArrayAdapter<String> adapter;

    public ProductAutoCompletion(Context context) {
        this.context = context;
        adapter = new ArrayAdapter<>(context, R.layout.dropdown_item, suggestions);
    }

    public void loadStore() {
        File dir = new File(context.getFilesDir(), "autocompletion");
        if (dir.exists()) {
            File store = new File(dir, "ids.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(store))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        addSuggestion(line);
                    }
                }
                adapter.notifyDataSetChanged();
            } catch (IOException e) {
                Log.d(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    public void updateStore(String suggestion) {
        if (suggestion != null && !suggestion.trim().isEmpty()) {
            suggestion = suggestion.trim();

            if (!suggestions.contains(suggestion)) {
                addSuggestion(suggestion);
                adapter.notifyDataSetChanged();

                new Thread(() -> {
                    if (!isUpdateInProgress.get()) {
                        isUpdateInProgress.set(true);
                        writeStoreToFile();
                        isUpdateInProgress.set(false);
                    }
                }).start();
            }
        }
    }

    private void addSuggestion(String suggestion) {
        int currentSize = suggestions.size();
        adapter.add(suggestion);
        if (suggestions.size() == currentSize) {
            suggestions.add(suggestion);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void writeStoreToFile() {
        File dir = new File(context.getFilesDir(), "autocompletion");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File store = new File(dir, "ids.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(store, false))) {
            for (String s : suggestions) {
                bw.write(s);
                bw.newLine();
            }
        } catch (IOException e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        }
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public int getThreshold() {
        return 1;
    }
}
