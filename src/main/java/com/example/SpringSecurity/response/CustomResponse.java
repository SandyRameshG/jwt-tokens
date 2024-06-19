package com.example.SpringSecurity.response;

import java.util.function.Function;

public class CustomResponse<T> {
    private int statusCode;
    private String statusDescription;
    private T data;

    public CustomResponse(int statusCode, String statusDescription, T data) {
        this.statusCode = validateStatusCode(statusCode);
        this.statusDescription = statusDescription;
        this.data = data;
    }

    private int validateStatusCode(int statusCode) {
        // Ensure that statusCode is a three or four-digit number
        if (statusCode < 100 || statusCode > 9999) {
            throw new IllegalArgumentException("statusCode must be a three or four-digit number.");
        }
        return statusCode;
    }

    public static <T> CustomResponse<T> success(int statusCode, String message, T data) {
        return new CustomResponse<>(statusCode, message, data);
    }

    public static <T> CustomResponse<T> error(int statusCode, String message, T data) {
        return new CustomResponse<>(statusCode, message, data);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public T getData() {
        return data;
    }

    // Allow covariance on the data type
    public <U> CustomResponse<U> mapData(Function<? super T, ? extends U> mapper) {
        return new CustomResponse<>(statusCode, statusDescription, mapper.apply(data));
    }
}
