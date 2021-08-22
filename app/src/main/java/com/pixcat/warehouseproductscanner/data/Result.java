package com.pixcat.warehouseproductscanner.data;

public class Result<T> {

    private final T data;
    private final Integer error;

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null);
    }

    public static <T> Result<T> failure(Integer error) {
        return new Result<>(null, error);
    }

    private Result(T data, Integer error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public Integer getError() {
        return error;
    }

    public boolean isSuccess() {
        return error == null;
    }
}
