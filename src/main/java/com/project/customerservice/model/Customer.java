package com.project.customerservice.model;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    private String phoneNumber;
    
    private String address;
    
    private String city;
    
    private String country;
    
    private String postalCode;
private Integer loyaltyPoints = 0;
    
    @Enumerated(EnumType.STRING)
    private LoyaltyTier loyaltyTier = LoyaltyTier.STANDARD;
    
    private LocalDateTime tierUpdatedAt;
    
    private Double lifetimePurchaseValue = 0.0;
    
    private Integer completedOrders = 0;
}