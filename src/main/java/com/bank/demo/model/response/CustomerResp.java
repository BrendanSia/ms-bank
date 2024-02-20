package com.bank.demo.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResp<T> {
    private String status;
    private T data;
    private String error;

    public CustomerResp(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public CustomerResp(String status, String error) {
        this.status = status;
        this.error = error;
    }
}
