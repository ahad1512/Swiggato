package com.example.Swiggato.service.Impl;

import com.example.Swiggato.dto.request.DeliveryPartnerRequest;
import com.example.Swiggato.model.DeliveryPartner;
import com.example.Swiggato.repository.DeliveryPartnerRepository;
import com.example.Swiggato.service.DeliveryPartnerService;
import com.example.Swiggato.transformer.DeliveryPartnerTransformer;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    final DeliveryPartnerRepository deliveryPartnerRepository;

    public DeliveryPartnerServiceImpl(DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    @Override
    public String addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {

        //model -->> Dto
        DeliveryPartner deliveryPartner = DeliveryPartnerTransformer.PartnerToPartnerRequest(deliveryPartnerRequest);

        //save delivery partner to db
        deliveryPartnerRepository.save(deliveryPartner);

        return "You are registered successfully";
    }
}
