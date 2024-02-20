package com.bank.demo.service;

import com.bank.demo.entity.Account;
import com.bank.demo.entity.Customer;
import com.bank.demo.model.CustomerDTO;
import com.bank.demo.repository.AccountRepository;
import com.bank.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;

    public CustomerDTO getCustomerById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElse(null);

        if (customer != null) {
            Account account = accountRepository.findByCustomerId(customerId);

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setAge(customer.getAge());
            if (account != null) {
                customerDTO.setAccountNo(account.getAccNo());
            }

            return customerDTO;
        } else {
            return null; // Or throw an exception if needed
        }
    }

    public Page<CustomerDTO> getCustomers(int page, int size) {
        return customerRepository.findAll(PageRequest.of(page, size))
                .map(customer -> {
                    CustomerDTO customerDTO = new CustomerDTO();
                    customerDTO.setId(customer.getId());
                    customerDTO.setName(customer.getName());
                    customerDTO.setAge(customer.getAge());
                    return customerDTO;
                });
    }

    @Transactional
    public void processCustomer(String action, CustomerDTO customerDTO) {
        switch (action.toUpperCase()) {
            case "CREATE":
                createCustomer(customerDTO);
                break;
            case "UPDATE":
                updateCustomer(customerDTO);
                break;
            case "DELETE":
                deleteCustomer(customerDTO.getId());
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

    private void createCustomer(CustomerDTO customerDTO) {
        // Set Customer Record
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName(customerDTO.getName());
        customer.setAge(customerDTO.getAge());
        customerRepository.save(customer);

        // Set Account Record
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setCustId(customer.getId());
        account.setAccNo(customerDTO.getAccountNo());
        accountRepository.save(account);
    }

    private void updateCustomer(CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerDTO.getId());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setName(customerDTO.getName());
            customer.setAge(customerDTO.getAge());
            customerRepository.save(customer);
        } else {
            throw new RuntimeException("Customer not found with id: " + customerDTO.getId());
        }
    }

    private void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
        accountRepository.deleteByCustId(customerId);
    }
}
