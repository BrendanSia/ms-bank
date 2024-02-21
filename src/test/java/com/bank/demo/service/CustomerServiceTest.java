package com.bank.demo.service;

import com.bank.demo.entity.Account;
import com.bank.demo.entity.Customer;
import com.bank.demo.model.CustomerDTO;
import com.bank.demo.repository.AccountRepository;
import com.bank.demo.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testGetCustomerById_CustomerFound() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Tester 1");

        Account account = new Account();
        account.setAccNo("123456789");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomerId(customerId)).thenReturn(account);

        CustomerDTO customerDTO = customerService.getCustomerById(customerId);

        assertEquals(customerId, customerDTO.getId());
        assertEquals("Tester 1", customerDTO.getName());
        assertEquals("123456789", customerDTO.getAccountNo());
    }

    @Test
    public void testGetCustomerById_CustomerNotFound() {
        UUID customerId = UUID.randomUUID();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        CustomerDTO customerDTO = customerService.getCustomerById(customerId);

        assertEquals(null, customerDTO);
    }

    @Test
    public void testGetCustomers() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Tester 1");

        Page<Customer> customerPage = new PageImpl<>(Collections.singletonList(customer));

        when(customerRepository.findAll(any(PageRequest.class))).thenReturn(customerPage);

        Page<CustomerDTO> customerDTOPage = customerService.getCustomers(0, 1);

        assertEquals(1, customerDTOPage.getTotalElements());
        assertEquals(customerId, customerDTOPage.getContent().get(0).getId());
        assertEquals("Tester 1", customerDTOPage.getContent().get(0).getName());
        Object firstElement = customerDTOPage.getContent().get(0);
    }

    @Test
    public void testProcessCustomer_CreateAction() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Tester 1");
        customerDTO.setAge(17);

        customerService.processCustomer("CREATE", customerDTO);

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testProcessCustomer_UpdateAction() {
        UUID customerId = UUID.randomUUID();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);
        customerDTO.setName("Jane Doe");
        customerDTO.setAge(35);

        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setName("Tester 1");
        existingCustomer.setAge(30);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        customerService.processCustomer("UPDATE", customerDTO);

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test(expected = RuntimeException.class)
    public void testProcessCustomer_UpdateAction_CustomerNotFound() {
        UUID customerId = UUID.randomUUID();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        customerService.processCustomer("UPDATE", customerDTO);
    }

    @Test
    public void testProcessCustomer_DeleteAction() {
        UUID customerId = UUID.randomUUID();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);

        customerService.processCustomer("DELETE", customerDTO);

        verify(customerRepository, times(1)).deleteById(customerId);
        verify(accountRepository, times(1)).deleteByCustId(customerId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessCustomer_InvalidAction() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerService.processCustomer("INVALID_ACTION", customerDTO);
    }

}