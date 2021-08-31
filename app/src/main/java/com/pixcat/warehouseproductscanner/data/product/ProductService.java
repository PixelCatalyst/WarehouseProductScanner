package com.pixcat.warehouseproductscanner.data.product;

import com.pixcat.warehouseproductscanner.data.model.ProductDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {

    @GET("/v1/products/{productId}")
    Call<ProductDto> getProductById(@Path("productId") String productId);

    @GET("/v1/barcodes/{barcode}")
    Call<ProductDto> getProductByBarcode(@Path("barcode") String barcode);

    @PUT("/v1/products/{productId}")
    Call<Void> putProduct(@Path("productId") String productId, @Body ProductDto productDto);
}
