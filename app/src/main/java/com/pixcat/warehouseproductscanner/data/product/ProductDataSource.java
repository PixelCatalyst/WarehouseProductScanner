package com.pixcat.warehouseproductscanner.data.product;

import android.util.Log;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.RetrofitServiceFactory;
import com.pixcat.warehouseproductscanner.data.model.ProductDto;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ProductDataSource {

    private static final String TAG = "ProductDataSource";

    private final ProductService productService;

    public ProductDataSource(String authToken) {
        productService = RetrofitServiceFactory.create(ProductService.class, authToken);
    }

    public Result<ProductDto> getProductByIdOrBarcode(String uniqueId) {
        try {
            Response<ProductDto> byIdResponse = productService.getProductById(uniqueId).execute();

            if (byIdResponse.isSuccessful()) {
                return Result.success(byIdResponse.body());
            } else if (byIdResponse.code() == 404) {
                Response<ProductDto> byBarcodeResponse = productService.getProductByBarcode(uniqueId).execute();

                if (byBarcodeResponse.isSuccessful()) {
                    return Result.success(byBarcodeResponse.body());
                } else if (byBarcodeResponse.code() == 404) {
                    return Result.failure(R.string.search_error_not_found);
                }
            }
            return Result.failure(R.string.search_error_server);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage(), e);

            return Result.failure(R.string.search_error_unknown);
        }
    }

    public Result<Boolean> putProduct(ProductDto productDto) {
        try {
            Response<Void> putResponse = productService.putProduct(productDto.getProductId(), productDto).execute();

            if (putResponse.isSuccessful()) {
                return Result.success(true);
            }

            Log.d(TAG, "Response code: " + putResponse.code());
            return Result.failure(R.string.create_error_server);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage(), e);

            return Result.failure(R.string.create_error_unknown);
        }
    }

    public Result<Boolean> putImage(String productId, byte[] buffer) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("image/png"), buffer);
            Response<Void> putResponse = productService.putImage(productId, body).execute();

            if (putResponse.isSuccessful()) {
                return Result.success(true);
            }

            Log.d(TAG, "Response code: " + putResponse.code());
            return Result.failure(R.string.create_error_server);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage(), e);

            return Result.failure(R.string.create_error_unknown);
        }
    }
}