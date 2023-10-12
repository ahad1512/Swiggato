package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.DeliveryPartnerRequest;
import com.example.Swiggato.model.DeliveryPartner;

import java.util.ArrayList;

public class DeliveryPartnerTransformer {

    public static DeliveryPartner PartnerToPartnerRequest (DeliveryPartnerRequest deliveryPartnerRequest){
        return DeliveryPartner.builder()
                .name(deliveryPartnerRequest.getName())
                .gender(deliveryPartnerRequest.getGender())
                .mobileNo(deliveryPartnerRequest.getMobileNo())
                .orders(new ArrayList<>())
                .build();
    }
}
