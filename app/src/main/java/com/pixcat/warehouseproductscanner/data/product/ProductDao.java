package com.pixcat.warehouseproductscanner.data.product;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pixcat.warehouseproductscanner.data.model.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM productentity WHERE id = :id")
    List<ProductEntity> getProducts(String id);

    @Insert(onConflict = REPLACE)
    void insert(ProductEntity entity);

    @Query("DELETE FROM productentity WHERE id = :id")
    void deleteById(String id);
}
