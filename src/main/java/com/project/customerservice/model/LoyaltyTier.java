package com.project.customerservice.model;



public enum LoyaltyTier {
    STANDARD(0, 0.0),
    SILVER(1000, 0.05),
    GOLD(5000, 0.10),
    PLATINUM(10000, 0.15);
    
    private final int pointsRequired;
    private final double discountPercentage;
    
    LoyaltyTier(int pointsRequired, double discountPercentage) {
        this.pointsRequired = pointsRequired;
        this.discountPercentage = discountPercentage;
    }
    
    public int getPointsRequired() {
        return pointsRequired;
    }
    
    public double getDiscountPercentage() {
        return discountPercentage;
    }
    
    public static LoyaltyTier calculateTier(int points) {
        if (points >= PLATINUM.getPointsRequired()) {
            return PLATINUM;
        } else if (points >= GOLD.getPointsRequired()) {
            return GOLD;
        } else if (points >= SILVER.getPointsRequired()) {
            return SILVER;
        } else {
            return STANDARD;
        }
    }
}