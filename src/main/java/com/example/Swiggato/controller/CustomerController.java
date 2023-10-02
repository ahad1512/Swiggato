package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CustomerResponse;
import com.example.Swiggato.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse response = customerService.addCustomer(customerRequest);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
    @GetMapping("/find/mobile/{mobile}")
    public ResponseEntity getCustomerByMobile(@PathVariable("mobile") String mobile){
        try{
            CustomerResponse customerResponse = customerService.findCustomerByMobile(mobile);
            return new ResponseEntity(customerResponse,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
