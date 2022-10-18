package com.sydney.service;

import com.sydney.model.Customer;
import com.sydney.repository.CustomerRepository;
import com.sydney.requests.CustomerRegistrationRequest;
import com.sydney.response.FraudCheckResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()

                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())

                .build();

        //todo: validate email
        //todo: store customer in db
        customerRepository.saveAndFlush(customer);//so that we can have access to customer id
        //todo: check if email is not taken
        //todo: check fraudster
        FraudCheckResponse response = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );
        if(response.isFraudster()){
            throw new IllegalStateException("Heeeeeeey check out customer is suspected fraudster");
        }
        //todo: send notification
    }
}
