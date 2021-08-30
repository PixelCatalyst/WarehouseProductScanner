package com.pixcat.warehouseproductscanner.ui.main.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.model.ActiveUser;

public class SearchFragment extends Fragment {

    private final ActiveUser activeUser;

    private SearchViewModel searchViewModel;

    public SearchFragment(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        AutoCompleteTextView searchTextView = view.findViewById(R.id.search_id);
        Button scanButton = view.findViewById(R.id.button_scan);
        Button searchButton = view.findViewById(R.id.button_search);

        searchViewModel = new ViewModelProvider(this, new SearchViewModelFactory(activeUser))
                .get(SearchViewModel.class);

        searchViewModel.getSearchFormState().observe(getViewLifecycleOwner(), searchFormState -> {
            if (searchFormState == null) {
                return;
            }
            searchButton.setEnabled(searchFormState.isDataValid());
            if (searchFormState.getIdError() != null) {
                searchTextView.setError(getString(searchFormState.getIdError()));
            }
        });
        searchViewModel.getSearchResult().observe(getViewLifecycleOwner(), searchResult -> {
            if (searchResult == null) {
                return;
            }
            if (searchResult.getError() != null) {
                // TODO handle errors via UI
                Toast.makeText(requireActivity().getApplicationContext(), searchResult.getError(), Toast.LENGTH_SHORT).show();
            }
            if (searchResult.getSuccess() != null) {
                Toast.makeText(requireActivity().getApplicationContext(), "TODO: search success", Toast.LENGTH_SHORT).show();
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
                searchViewModel.searchFormChanged(searchTextView.getText().toString());
            }
        };
        searchTextView.addTextChangedListener(afterTextChangedListener);
        searchTextView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchViewModel.search(searchTextView.getText().toString().trim());
            }
            return false;
        });

        scanButton.setOnClickListener(v -> Toast
                .makeText(requireActivity().getApplicationContext(), "TODO: launch camera", Toast.LENGTH_SHORT)
                .show()
        );

        searchButton.setOnClickListener(v -> searchViewModel.search(searchTextView.getText().toString().trim()));

        return view;
    }
}
