package com.project.customerservice.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import com.project.customerservice.model.LoyaltyTier;
import java.time.LocalDateTime;

@Data
public class CustomerDto {
    private Long id;
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    private String phoneNumber;
    
    private String address;
    
    private String city;
    
    private String country;
    
    private String postalCode;
    
    // Loyalty related fields
    private Integer loyaltyPoints;
    
    private LoyaltyTier loyaltyTier;
    
    private LocalDateTime tierUpdatedAt;
    
    private Double lifetimePurchaseValue;
    
    private Integer completedOrders;
    
    // Helper field for discount info
    private Double discountPercentage;
}