package com.project.customerservice.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.customerservice.dto.CustomerDto;
import com.project.customerservice.dto.LoyaltyActivityDto;
import com.project.customerservice.exception.ResourceNotFoundException;
import com.project.customerservice.model.Customer;
import com.project.customerservice.model.LoyaltyActivity;
import com.project.customerservice.model.LoyaltyTier;
import com.project.customerservice.model.LoyaltyActivityType;
import com.project.customerservice.repository.CustomerRepository;
import com.project.customerservice.repository.LoyaltyActivityRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoyaltyService {

    private final CustomerRepository customerRepository;
    private final LoyaltyActivityRepository loyaltyActivityRepository;
    
    @Autowired
    public LoyaltyService(CustomerRepository customerRepository, LoyaltyActivityRepository loyaltyActivityRepository) {
        this.customerRepository = customerRepository;
        this.loyaltyActivityRepository = loyaltyActivityRepository;
    }
    
    public CustomerDto getLoyaltyInfo(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        
        // Initialize loyalty fields if they're null
        if (customer.getLoyaltyTier() == null) {
            customer.setLoyaltyTier(LoyaltyTier.STANDARD);
        }
        if (customer.getLoyaltyPoints() == null) {
            customer.setLoyaltyPoints(0);
        }
        if (customer.getLifetimePurchaseValue() == null) {
            customer.setLifetimePurchaseValue(0.0);
        }
        if (customer.getCompletedOrders() == null) {
            customer.setCompletedOrders(0);
        }
        
        // Save the updated customer
        customer = customerRepository.save(customer);
        
        CustomerDto customerDto = new CustomerDto();
        org.springframework.beans.BeanUtils.copyProperties(customer, customerDto);
        
        // Calculate discount percentage based on tier
        customerDto.setDiscountPercentage(customer.getLoyaltyTier().getDiscountPercentage());
        
        return customerDto;
    }
    
    
    @Transactional
    public CustomerDto addLoyaltyPoints(Long customerId, LoyaltyActivityDto activityDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + activityDto.getPoints());
        
        // Update lifetime value if it's a purchase
        if (activityDto.getType() == LoyaltyActivityType.PURCHASE) {
            customer.setLifetimePurchaseValue(customer.getLifetimePurchaseValue() + activityDto.getAmount());
            customer.setCompletedOrders(customer.getCompletedOrders() + 1);
        }
        
        // Recalculate loyalty tier
        LoyaltyTier newTier = LoyaltyTier.calculateTier(customer.getLoyaltyPoints());
        if (newTier != customer.getLoyaltyTier()) {
            customer.setLoyaltyTier(newTier);
            customer.setTierUpdatedAt(LocalDateTime.now());
        }
        
        // Save the customer
        Customer updatedCustomer = customerRepository.save(customer);
        
        // Record the activity
        LoyaltyActivity activity = new LoyaltyActivity();
        activity.setCustomer(customer);
        activity.setType(activityDto.getType());
        activity.setPoints(activityDto.getPoints());
        activity.setAmount(activityDto.getAmount());
        activity.setDescription(activityDto.getDescription());
        activity.setReferenceId(activityDto.getReferenceId());
        activity.setCreatedAt(LocalDateTime.now());
        loyaltyActivityRepository.save(activity);
        
        // Map to DTO
        CustomerDto customerDto = new CustomerDto();
        org.springframework.beans.BeanUtils.copyProperties(updatedCustomer, customerDto);
        customerDto.setDiscountPercentage(updatedCustomer.getLoyaltyTier().getDiscountPercentage());
        
        return customerDto;
    }
    
    public List<LoyaltyActivityDto> getLoyaltyActivities(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        
        List<LoyaltyActivity> activities = loyaltyActivityRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
        return activities.stream()
                .map(this::mapToActivityDto)
                .collect(Collectors.toList());
    }
    
    private LoyaltyActivityDto mapToActivityDto(LoyaltyActivity activity) {
        LoyaltyActivityDto dto = new LoyaltyActivityDto();
        org.springframework.beans.BeanUtils.copyProperties(activity, dto);
        dto.setCustomerId(activity.getCustomer().getId());
        return dto;
    }
}