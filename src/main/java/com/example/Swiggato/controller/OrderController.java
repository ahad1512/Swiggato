package com.example.Swiggato.controller;


import com.example.Swiggato.dto.response.OrderResponse;
import com.example.Swiggato.service.Impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    final OrderServiceImpl orderServiceImpl;

    public OrderController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @PostMapping("place/mobile/{mobile}")
    public ResponseEntity placeOrder(@PathVariable("mobile") String customerMobileNo){
        try {
            OrderResponse orderResponse = orderServiceImpl.placeOrder(customerMobileNo);
            return new ResponseEntity(orderResponse, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
