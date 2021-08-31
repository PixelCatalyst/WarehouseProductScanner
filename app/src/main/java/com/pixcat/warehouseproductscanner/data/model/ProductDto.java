package com.pixcat.warehouseproductscanner.data.model;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    private final String productId;
    private final String description;
    private final String storageTemperature;
    private final Integer heightInMillimeters;
    private final Integer widthInMillimeters;
    private final Integer lengthInMillimeters;
    private final BigDecimal weightInKilograms;
    private final List<String> barcodes;

    public ProductDto(
            String productId,
            String description,
            String storageTemperature,
            Integer heightInMillimeters,
            Integer widthInMillimeters,
            Integer lengthInMillimeters,
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

    public static Builder builder() {
        return new Builder();
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

    public Integer getHeightInMillimeters() {
        return this.heightInMillimeters;
    }

    public Integer getWidthInMillimeters() {
        return this.widthInMillimeters;
    }

    public Integer getLengthInMillimeters() {
        return this.lengthInMillimeters;
    }

    public BigDecimal getWeightInKilograms() {
        return this.weightInKilograms;
    }

    public List<String> getBarcodes() {
        return this.barcodes;
    }

    public static class Builder {

        private String productId;
        private String description;
        private String storageTemperature;
        private Integer heightInMillimeters;
        private Integer widthInMillimeters;
        private Integer lengthInMillimeters;
        private BigDecimal weightInKilograms;
        private List<String> barcodes;

        public Builder() {
        }

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder storageTemperature(String storageTemperature) {
            this.storageTemperature = storageTemperature;
            return this;
        }

        public Builder heightInMillimeters(Integer heightInMillimeters) {
            this.heightInMillimeters = heightInMillimeters;
            return this;
        }

        public Builder widthInMillimeters(Integer widthInMillimeters) {
            this.widthInMillimeters = widthInMillimeters;
            return this;
        }

        public Builder lengthInMillimeters(Integer lengthInMillimeters) {
            this.lengthInMillimeters = lengthInMillimeters;
            return this;
        }

        public Builder weightInKilograms(BigDecimal weightInKilograms) {
            this.weightInKilograms = weightInKilograms;
            return this;
        }

        public Builder barcodes(List<String> barcodes) {
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
