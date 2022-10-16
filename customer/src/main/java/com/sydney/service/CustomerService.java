package com.sydney.service;

import com.sydney.model.Customer;
import com.sydney.repository.CustomerRepository;
import com.sydney.requests.CustomerRegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()

                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())

                .build();

        //todo: validate email
        //todo: store customer in db
        customerRepository.save(customer);
        //todo: check if email is not taken
    }
}
