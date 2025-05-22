package com.project.customerservice.controller;



import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.customerservice.dto.CustomerDto;
import com.project.customerservice.dto.LoyaltyActivityDto;
import com.project.customerservice.service.LoyaltyService;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/loyalty")
public class LoyaltyController {

    private final LoyaltyService loyaltyService;
    
    @Autowired
    public LoyaltyController(LoyaltyService loyaltyService) {
        this.loyaltyService = loyaltyService;
    }
    
    @GetMapping
    public ResponseEntity<CustomerDto> getLoyaltyInfo(@PathVariable Long customerId) {
        return ResponseEntity.ok(loyaltyService.getLoyaltyInfo(customerId));
    }
    
    @PostMapping("/points")
    public ResponseEntity<CustomerDto> addLoyaltyPoints(
            @PathVariable Long customerId,
            @Valid @RequestBody LoyaltyActivityDto activityDto) {
        return ResponseEntity.ok(loyaltyService.addLoyaltyPoints(customerId, activityDto));
    }
    
    @GetMapping("/activities")
    public ResponseEntity<List<LoyaltyActivityDto>> getLoyaltyActivities(@PathVariable Long customerId) {
        return ResponseEntity.ok(loyaltyService.getLoyaltyActivities(customerId));
    }
}