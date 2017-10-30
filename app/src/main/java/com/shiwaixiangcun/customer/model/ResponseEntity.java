package com.shiwaixiangcun.customer.model;

/**
 * Created by fyj on 2017/5/24.
 *  http返回通用model
 */
public class ResponseEntity<T> {

    private String message;
    private int responseCode;
    private boolean success;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "message='" + message + '\'' +
                ", responseCode=" + responseCode +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
