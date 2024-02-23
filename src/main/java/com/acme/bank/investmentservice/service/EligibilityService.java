package com.acme.bank.investmentservice.service;

import com.acme.bank.investmentservice.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EligibilityService {

    @Autowired
    private UserProfileService userProfileService;

    // UserProfile retrieval is an intensive call, hence we want to cache the operation
    // No need to consider cache invalidation for simplicity
    private HashMap<String, UserProfile> cache;

    public boolean checkInvestmentEligibility(String userId) {
        UserProfile up = null;
        if (cache.get(userId) == null) {
            up = userProfileService.findByUserId(userId);
            cache.put(userId, up);
        } else {
            up = cache.get(userId);
        }

        // checking whether user has sufficient deposit balance with respect to his/her age
        // Age >= 65, deposit balance must be >= 1 million
        // Age <= 65, deposit balance must be >= 0.5 million
        try {
            if (up.getUserPersonalInformation().getAge() > 65 && up.getUserIncomeInformation().getDepositBalanceInUSD() >= 1_000_000f) {
                return true;
            } else if (up.getUserPersonalInformation().getAge() <= 65) {
                if (up.getUserIncomeInformation().getDepositBalanceInUSD() >= 500_000f) {
                    return true;
                } else {
                    return false;
                }
            } else {
                throw new IllegalArgumentException("Invalid user profile");
            }
        } catch (Exception e) {
            return false;
        }
    }
}
