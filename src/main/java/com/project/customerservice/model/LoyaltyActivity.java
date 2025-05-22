package com.project.customerservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "loyalty_activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    private LoyaltyActivityType type;
    
    private Integer points;
    
    private Double amount;
    
    private String description;
    
    private String referenceId; // e.g., order ID
    
    private LocalDateTime createdAt;
}