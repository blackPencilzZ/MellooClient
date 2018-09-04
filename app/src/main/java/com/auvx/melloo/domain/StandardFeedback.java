package com.auvx.melloo.domain;

public class StandardFeedback<T> {

    private T data;
    private String desc;
    private Integer statusCode;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "StandardFeedback{" +
                "data=" + data +
                ", desc='" + desc + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
