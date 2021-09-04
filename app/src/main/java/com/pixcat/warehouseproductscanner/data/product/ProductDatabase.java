package com.pixcat.warehouseproductscanner.data.product;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.pixcat.warehouseproductscanner.data.model.ProductEntity;

@Database(entities = {ProductEntity.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
}
