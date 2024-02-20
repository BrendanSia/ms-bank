package com.bank.demo.model.response;

import com.bank.demo.model.CustomerDTO;
import lombok.Data;

import java.util.List;

@Data
public class CustomerListResp {
    private String status;
    private List<CustomerDTO> data;
    private long totalElements;

    public CustomerListResp(String status, List<CustomerDTO> data, long totalElements) {
        this.status = status;
        this.data = data;
        this.totalElements = totalElements;
    }
}
