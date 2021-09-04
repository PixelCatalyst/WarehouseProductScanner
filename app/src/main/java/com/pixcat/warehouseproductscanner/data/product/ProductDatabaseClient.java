package com.pixcat.warehouseproductscanner.data.product;

import android.content.Context;

import androidx.room.Room;

public class ProductDatabaseClient {

    private static ProductDatabaseClient mInstance;

    private final ProductDatabase db;

    private ProductDatabaseClient(Context appCtx) {

        db = Room.databaseBuilder(appCtx, ProductDatabase.class, "product-local-db").build();
    }

    public static synchronized ProductDatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new ProductDatabaseClient(mCtx);
        }
        return mInstance;
    }

    public ProductDatabase getDb() {
        return db;
    }
}
