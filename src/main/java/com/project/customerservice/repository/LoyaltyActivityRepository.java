package com.project.customerservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.project.customerservice.model.LoyaltyActivity;

import java.util.List;

public interface LoyaltyActivityRepository extends JpaRepository<LoyaltyActivity, Long> {
    List<LoyaltyActivity> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}