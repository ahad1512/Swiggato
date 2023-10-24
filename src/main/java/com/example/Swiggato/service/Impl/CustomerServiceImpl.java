package com.example.Swiggato.service.Impl;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CustomerResponse;
import com.example.Swiggato.exceptions.CustomerNotFoundException;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.service.CustomerService;
import com.example.Swiggato.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        //dto --> model
        Customer customer = CustomerTransformer.CustomerRequestToCustomer(customerRequest);

        //allocate a cart
        Cart cart =Cart.builder()
                .cartTotal(0)
                .customer(customer)
                .build();

         customer.setCart(cart);

        //Save both customer and cart
        Customer savedCustomer = customerRepository.save(customer);
        //model -->> dto
        return CustomerTransformer.CustomerToCustomerResponse(savedCustomer);
    }

    @Override
    public CustomerResponse findCustomerByMobile(String mobile) {

        Customer customer = customerRepository.findByMobileNo(mobile);
        if(customer==null){
            throw new CustomerNotFoundException("Invalid mobile no!!!");
        }
        return CustomerTransformer.CustomerToCustomerResponse(customer);
    }
}

