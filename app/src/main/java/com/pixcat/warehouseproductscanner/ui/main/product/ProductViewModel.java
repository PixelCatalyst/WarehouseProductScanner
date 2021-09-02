package com.pixcat.warehouseproductscanner.ui.main.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.model.ProductDto;
import com.pixcat.warehouseproductscanner.data.product.ProductDataSource;

import java.math.BigDecimal;

public class ProductViewModel extends ViewModel {

    private final MutableLiveData<ProductFormState> productFormState = new MutableLiveData<>();
    private final MutableLiveData<CreateProductResult> createProductResult = new MutableLiveData<>();
    private final ProductDataSource productDataSource;

    public ProductViewModel(ProductDataSource productDataSource) {
        this.productDataSource = productDataSource;
    }

    LiveData<ProductFormState> getProductFormState() {
        return productFormState;
    }

    LiveData<CreateProductResult> getCreateProductResult() {
        return createProductResult;
    }

    public void consumeCreateProductResult() {
        createProductResult.postValue(null);
    }

    public void createProduct(ProductDto productDto) {
        new Thread(() -> {
            Result<Boolean> result = productDataSource.putProduct(productDto);

            if (result.isSuccess()) {
                createProductResult.postValue(new CreateProductResult(productDto));
            } else {
                createProductResult.postValue(new CreateProductResult(result.getError()));
            }
        }).start();
    }

    public void productFormChanged(ProductDto productDto) {
        productFormState.setValue(new ProductFormState.Builder()
                .productIdError(productIdShouldBePresent(productDto.getProductId()))
                .heightError(heightShouldBePresentAndAboveZero(productDto.getHeightInMillimeters()))
                .widthError(widthShouldBePresentAndAboveZero(productDto.getWidthInMillimeters()))
                .lengthError(lengthShouldBePresentAndAboveZero(productDto.getLengthInMillimeters()))
                .weightError(weightShouldBePresentAndAboveZero(productDto.getWeightInKilograms()))
                .build()
        );
    }

    private Integer productIdShouldBePresent(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            return R.string.product_id_required;
        }
        return null;
    }

    private Integer heightShouldBePresentAndAboveZero(Integer height) {
        if (height == null) {
            return R.string.height_required;
        } else if (height <= 0) {
            return R.string.height_above_zero;
        }
        return null;
    }

    private Integer widthShouldBePresentAndAboveZero(Integer width) {
        if (width == null) {
            return R.string.width_required;
        } else if (width <= 0) {
            return R.string.width_above_zero;
        }
        return null;
    }

    private Integer lengthShouldBePresentAndAboveZero(Integer length) {
        if (length == null) {
            return R.string.length_required;
        } else if (length <= 0) {
            return R.string.length_above_zero;
        }
        return null;
    }

    private Integer weightShouldBePresentAndAboveZero(BigDecimal weight) {
        if (weight == null) {
            return R.string.weight_required;
        } else if (weight.compareTo(BigDecimal.ZERO) <= 0) {
            return R.string.weight_above_zero;
        }
        return null;
    }
}
