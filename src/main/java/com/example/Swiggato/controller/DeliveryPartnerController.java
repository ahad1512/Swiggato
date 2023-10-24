package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.DeliveryPartnerRequest;
import com.example.Swiggato.service.Impl.DeliveryPartnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-partner")
public class DeliveryPartnerController {

    final DeliveryPartnerServiceImpl deliveryPartnerServiceImpl;

    @Autowired
    public DeliveryPartnerController(DeliveryPartnerServiceImpl deliveryPartnerServiceImpl) {
        this.deliveryPartnerServiceImpl = deliveryPartnerServiceImpl;
    }

    @PostMapping("/add")
    public ResponseEntity addDeliveryPartner (@RequestBody DeliveryPartnerRequest deliveryPartnerRequest){
        try {
            String message = deliveryPartnerServiceImpl.addDeliveryPartner(deliveryPartnerRequest);
            return new ResponseEntity(message, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
