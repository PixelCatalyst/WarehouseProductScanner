package com.pixcat.warehouseproductscanner.ui.main.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.model.ProductDto;
import com.pixcat.warehouseproductscanner.data.product.ProductDataSource;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<SearchFormState> searchFormState = new MutableLiveData<>();
    private final MutableLiveData<SearchResult> searchResult = new MutableLiveData<>();
    private final ProductDataSource productDataSource;

    public SearchViewModel(ProductDataSource productDataSource) {
        this.productDataSource = productDataSource;
    }

    LiveData<SearchFormState> getSearchFormState() {
        return searchFormState;
    }

    LiveData<SearchResult> getSearchResult() {
        return searchResult;
    }

    public void consumeSearchResult() {
        searchResult.postValue(null);
    }

    public void search(String uniqueId) {
        new Thread(() -> {
            Result<ProductDto> result = productDataSource.getProductByIdOrBarcode(uniqueId);

            if (result.isSuccess()) {
                searchResult.postValue(new SearchResult(uniqueId, result.getData()));
            } else {
                searchResult.postValue(new SearchResult(uniqueId, result.getError()));
            }
        }).start();
    }

    public void searchFormChanged(String uniqueId) {
        if (!isIdValid(uniqueId)) {
            searchFormState.setValue(new SearchFormState(R.string.invalid_product_id));
        } else {
            searchFormState.setValue(new SearchFormState(true));
        }
    }

    private boolean isIdValid(String uniqueId) {
        return uniqueId != null && !uniqueId.trim().isEmpty();
    }
}
