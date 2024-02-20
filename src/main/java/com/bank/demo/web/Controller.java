package com.bank.demo.web;

import com.bank.demo.model.CustomerDTO;
import com.bank.demo.model.response.CustomerListResp;
import com.bank.demo.model.response.CustomerResp;
import com.bank.demo.service.CustomerService;
import com.bank.demo.service.ThirdPartyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class Controller {

    //private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ThirdPartyApiService thirdPartyApiService;


    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResp<CustomerDTO>> getCustomerById(
            @PathVariable("customerId") UUID customerId
    ) {
        try {
            CustomerDTO customerDTO = customerService.getCustomerById(customerId);
            if (customerDTO != null) {
                return ResponseEntity.ok().body(new CustomerResp<>("SUCCESS", customerDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomerResp<>("FAILED", "Customer not found with id: " + customerId));
            }
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<CustomerListResp> getCustomers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<CustomerDTO> customerPage = customerService.getCustomers(page - 1, size);
            return ResponseEntity.ok().body(new CustomerListResp("SUCCESS", customerPage.getContent(), customerPage.getTotalElements()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/processCustomer")
    public ResponseEntity<String> processCustomer(
            @RequestParam String action,
            @RequestBody CustomerDTO customerDTO) {
        try {
            customerService.processCustomer(action, customerDTO);
            return ResponseEntity.ok("Customer " + action.toLowerCase() + "d successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process customer");
        }
    }

    @GetMapping("/callThirdPartyApi")
    public ResponseEntity<String> callThirdPartyApi() {
        try {
            String response = thirdPartyApiService.callThirdPartyApi();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to call third-party API");
        }
    }
}
