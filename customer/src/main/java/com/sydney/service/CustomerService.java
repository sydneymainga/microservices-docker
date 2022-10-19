package com.sydney.service;

import com.sydney.model.Customer;
import com.sydney.repository.CustomerRepository;
import com.sydney.requests.CustomerRegistrationRequest;
import com.sydney.requests.NotificationRequest;
import com.sydney.response.FraudCheckResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
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
        NotificationRequest notificationRequest = new NotificationRequest();

        NotificationRequest request1 = NotificationRequest.builder()
                .toCustomerId(customer.getId())
                .toCustomerEmail(customer.getEmail())
                .message("Dear "+customer.getFirstname() +""+ customer.getLastname()+"\n"
                +"Welcome on board we are happy to sign you up,thank you for choosing us")
                .toCustomerName(customer.getFirstname() +""+ customer.getLastname())
                .build();
        log.info("we are trying to send this email to notification service ==> "+request1.getMessage());

        try {
             notificationRequest = restTemplate.postForObject(
                    "http://NOTIFICATION/api/v1/notification/",
                     request1,
                    NotificationRequest.class
            );
        }catch(Exception e){
            log.info("notification not sent "+ e.getMessage());
        }
    }
}
