package com.sydney.controller;

import com.sydney.requests.CustomerRegistrationRequest;
import com.sydney.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public void RegisterCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest ){
        log.info("register new customer{}",customerRegistrationRequest);
        customerService.registerCustomer(customerRegistrationRequest);

    }

}
