package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.DeliveryPartnerRequest;
import com.example.Swiggato.model.DeliveryPartner;
import com.example.Swiggato.repository.DeliveryPartnerRepository;
import com.example.Swiggato.transformer.DeliveryPartnerTransformer;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerService {

    final DeliveryPartnerRepository deliveryPartnerRepository;

    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    public String addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {

        //model -->> Dto
        DeliveryPartner deliveryPartner = DeliveryPartnerTransformer.PartnerToPartnerRequest(deliveryPartnerRequest);

        //save delivery partner to db
        deliveryPartnerRepository.save(deliveryPartner);

        return "You are registered successfully";
    }
}
