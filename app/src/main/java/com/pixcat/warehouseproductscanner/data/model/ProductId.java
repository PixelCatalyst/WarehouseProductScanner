package com.pixcat.warehouseproductscanner.data.model;

import java.util.Objects;

public class ProductId {

    public final String value;

    public ProductId(String value) {
        if (value == null || value.isEmpty() || value.trim().isEmpty()) {
            throw new IllegalArgumentException("ProductId must be present and not blank");
        }
        this.value = value;
    }

    public static ProductId of(String value) {
        return new ProductId(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductId)) return false;
        final ProductId other = (ProductId) o;
        if (!other.canEqual((Object) this)) return false;
        return Objects.equals(this.value, other.value);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductId;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $value = this.value;
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        return result;
    }
}
