package com.pixcat.warehouseproductscanner.data.model;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    private final String productId;
    private final String description;
    private final String storageTemperature;
    private final int heightInMillimeters;
    private final int widthInMillimeters;
    private final int lengthInMillimeters;
    private final BigDecimal weightInKilograms;
    private final List<String> barcodes;

    public ProductDto(
            String productId,
            String description,
            String storageTemperature,
            int heightInMillimeters,
            int widthInMillimeters,
            int lengthInMillimeters,
            BigDecimal weightInKilograms,
            List<String> barcodes) {
        this.productId = productId;
        this.description = description;
        this.storageTemperature = storageTemperature;
        this.heightInMillimeters = heightInMillimeters;
        this.widthInMillimeters = widthInMillimeters;
        this.lengthInMillimeters = lengthInMillimeters;
        this.weightInKilograms = weightInKilograms;
        this.barcodes = barcodes;
    }

    public static ProductDtoBuilder builder() {
        return new ProductDtoBuilder();
    }

    public String getProductId() {
        return this.productId;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStorageTemperature() {
        return this.storageTemperature;
    }

    public int getHeightInMillimeters() {
        return this.heightInMillimeters;
    }

    public int getWidthInMillimeters() {
        return this.widthInMillimeters;
    }

    public int getLengthInMillimeters() {
        return this.lengthInMillimeters;
    }

    public BigDecimal getWeightInKilograms() {
        return this.weightInKilograms;
    }

    public List<String> getBarcodes() {
        return this.barcodes;
    }

    public static class ProductDtoBuilder {
        private String productId;
        private String description;
        private String storageTemperature;
        private int heightInMillimeters;
        private int widthInMillimeters;
        private int lengthInMillimeters;
        private BigDecimal weightInKilograms;
        private List<String> barcodes;

        ProductDtoBuilder() {
        }

        public ProductDtoBuilder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public ProductDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductDtoBuilder storageTemperature(String storageTemperature) {
            this.storageTemperature = storageTemperature;
            return this;
        }

        public ProductDtoBuilder heightInMillimeters(int heightInMillimeters) {
            this.heightInMillimeters = heightInMillimeters;
            return this;
        }

        public ProductDtoBuilder widthInMillimeters(int widthInMillimeters) {
            this.widthInMillimeters = widthInMillimeters;
            return this;
        }

        public ProductDtoBuilder lengthInMillimeters(int lengthInMillimeters) {
            this.lengthInMillimeters = lengthInMillimeters;
            return this;
        }

        public ProductDtoBuilder weightInKilograms(BigDecimal weightInKilograms) {
            this.weightInKilograms = weightInKilograms;
            return this;
        }

        public ProductDtoBuilder barcodes(List<String> barcodes) {
            this.barcodes = barcodes;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(
                    productId,
                    description,
                    storageTemperature,
                    heightInMillimeters,
                    widthInMillimeters,
                    lengthInMillimeters,
                    weightInKilograms,
                    barcodes);
        }
    }
}
