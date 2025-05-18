package com.project.customerservice.service;



import java.util.List;

import com.project.customerservice.dto.CustomerDto;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    
    CustomerDto getCustomerById(Long id);
    
    List<CustomerDto> getAllCustomers();
    
    CustomerDto updateCustomer(Long id, CustomerDto customerDto);
    
    void deleteCustomer(Long id);
    
    List<CustomerDto> searchCustomers(String query);
    
    CustomerDto getCustomerByEmail(String email);
}
