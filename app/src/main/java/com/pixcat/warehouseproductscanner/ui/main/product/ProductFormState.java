package com.pixcat.warehouseproductscanner.ui.main.product;

public class ProductFormState {

    private final Integer productIdError;
    private final Integer heightError;
    private final Integer widthError;
    private final Integer lengthError;
    private final Integer weightError;

    private ProductFormState(
            Integer productIdError,
            Integer heightError,
            Integer widthError,
            Integer lengthError,
            Integer weightError) {
        this.productIdError = productIdError;
        this.heightError = heightError;
        this.widthError = widthError;
        this.lengthError = lengthError;
        this.weightError = weightError;
    }

    public boolean isDataValid() {
        return productIdError == null &&
                heightError == null &&
                widthError == null &&
                lengthError == null &&
                weightError == null;
    }

    public Integer getProductIdError() {
        return productIdError;
    }

    public Integer getHeightError() {
        return heightError;
    }

    public Integer getWidthError() {
        return widthError;
    }

    public Integer getLengthError() {
        return lengthError;
    }

    public Integer getWeightError() {
        return weightError;
    }

    public static class Builder {

        private Integer productIdError;
        private Integer heightError;
        private Integer widthError;
        private Integer lengthError;
        private Integer weightError;

        public Builder productIdError(Integer productIdError) {
            this.productIdError = productIdError;
            return this;
        }

        public Builder heightError(Integer heightError) {
            this.heightError = heightError;
            return this;
        }

        public Builder widthError(Integer widthError) {
            this.widthError = widthError;
            return this;
        }

        public Builder lengthError(Integer lengthError) {
            this.lengthError = lengthError;
            return this;
        }

        public Builder weightError(Integer weightError) {
            this.weightError = weightError;
            return this;
        }

        public ProductFormState build() {
            return new ProductFormState(productIdError, heightError, widthError, lengthError, weightError);
        }
    }
}
