package com.bank.demo.web;

import com.bank.demo.model.CustomerDTO;
import com.bank.demo.model.response.CustomerListResp;
import com.bank.demo.model.response.CustomerResp;
import com.bank.demo.service.CustomerService;
import com.bank.demo.service.ThirdPartyApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    private CustomerService customerService;
    @Mock
    private ThirdPartyApiService thirdPartyApiService;
    @InjectMocks
    private Controller controller;

    @Test
    public void testGetCustomerById_CustomerFound() {
        UUID customerId = UUID.randomUUID();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);
        customerDTO.setName("John Doe");

        when(customerService.getCustomerById(customerId)).thenReturn(customerDTO);

        ResponseEntity<CustomerResp<CustomerDTO>> responseEntity = controller.getCustomerById(customerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody().getStatus());
        assertEquals(customerDTO, responseEntity.getBody().getData());
    }

    @Test
    public void testGetCustomerById_CustomerNotFound() {
        UUID customerId = UUID.randomUUID();

        when(customerService.getCustomerById(customerId)).thenReturn(null);

        ResponseEntity<CustomerResp<CustomerDTO>> responseEntity = controller.getCustomerById(customerId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("FAILED", responseEntity.getBody().getStatus());
        assertEquals("Customer not found with id: " + customerId, responseEntity.getBody().getError());
    }

    @Test
    public void testGetCustomers() {
        List<CustomerDTO> customerList = new ArrayList<>();
        customerList.add(new CustomerDTO());
        Page<CustomerDTO> customerPage = new PageImpl<>(customerList);

        when(customerService.getCustomers(anyInt(), anyInt())).thenReturn(customerPage);

        ResponseEntity<CustomerListResp> responseEntity = controller.getCustomers(1, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody().getStatus());
        assertEquals(customerList, responseEntity.getBody().getData());
        assertEquals(customerList.size(), responseEntity.getBody().getTotalElements());
    }

    @Test
    public void testProcessCustomer() {
        String action = "UPDATE";
        CustomerDTO customerDTO = new CustomerDTO();

        ResponseEntity<String> responseEntity = controller.processCustomer(action, customerDTO);

        verify(customerService, times(1)).processCustomer(eq(action), eq(customerDTO));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Customer updated successfully", responseEntity.getBody());
    }

    @Test
    public void testCallThirdPartyApi() {
        String thirdPartyResponse = "Response from third-party API";

        when(thirdPartyApiService.callThirdPartyApi()).thenReturn(thirdPartyResponse);

        ResponseEntity<String> responseEntity = controller.callThirdPartyApi();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(thirdPartyResponse, responseEntity.getBody());
    }
}
