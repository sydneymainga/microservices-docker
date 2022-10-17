package com.sydney.service;

import com.sydney.model.FraudCheckHistory;
import com.sydney.repository.FraudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor

public class FraudService {

    private final FraudRepository fraudRepository;

    public boolean isFradulentCustomer(Integer customerID){

        fraudRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerID)
                        .CreatedAt(LocalDateTime.now())
                        .isFraudster(false)
                .build());

        //todo:thirdparty system to check if isfraud
        //todo:your own ways to check
        return false;
    }
}
