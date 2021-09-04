package com.pixcat.warehouseproductscanner.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class ProductEntity {

    public static ProductEntity of(String id, ProductDto dto) {
        return new ProductEntity(
                id,
                dto.getProductId(),
                dto.getDescription(),
                dto.getStorageTemperature(),
                dto.getHeightInMillimeters(),
                dto.getWidthInMillimeters(),
                dto.getLengthInMillimeters(),
                dto.getWeightInKilograms() == null ? null : dto.getWeightInKilograms().toPlainString(),
                stringifyBarcodes(dto.getBarcodes()));
    }

    private static String stringifyBarcodes(List<String> barcodes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String b : barcodes) {
            stringBuilder.append(b);
            stringBuilder.append(';');
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", description='" + description + '\'' +
                ", storageTemperature='" + storageTemperature + '\'' +
                ", heightInMillimeters=" + heightInMillimeters +
                ", widthInMillimeters=" + widthInMillimeters +
                ", lengthInMillimeters=" + lengthInMillimeters +
                ", weightInKilograms='" + weightInKilograms + '\'' +
                ", barcodes='" + barcodes + '\'' +
                '}';
    }

    public ProductEntity(
            @NonNull String id,
            String productId,
            String description,
            String storageTemperature,
            Integer heightInMillimeters,
            Integer widthInMillimeters,
            Integer lengthInMillimeters,
            String weightInKilograms,
            String barcodes) {
        this.id = id;
        this.productId = productId;
        this.description = description;
        this.storageTemperature = storageTemperature;
        this.heightInMillimeters = heightInMillimeters;
        this.widthInMillimeters = widthInMillimeters;
        this.lengthInMillimeters = lengthInMillimeters;
        this.weightInKilograms = weightInKilograms;
        this.barcodes = barcodes;
    }

    @NonNull
    @PrimaryKey
    public String id;

    @ColumnInfo(name = "productId")
    public String productId;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "temperature")
    public String storageTemperature;

    @ColumnInfo(name = "height")
    public Integer heightInMillimeters;

    @ColumnInfo(name = "width")
    public Integer widthInMillimeters;

    @ColumnInfo(name = "length")
    public Integer lengthInMillimeters;

    @ColumnInfo(name = "weight")
    public String weightInKilograms;

    @ColumnInfo(name = "barcodes")
    public String barcodes;
}
