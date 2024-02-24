package com.acme.bank.investmentservice.service;

import com.acme.bank.investmentservice.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class EligibilityService {

    @Autowired
    private UserProfileService userProfileService;

    private ArrayList<UserProfile> cache = new ArrayList<>();

    /**
     * Fetch UserProfile by userId in cache
     * If not found, fetch it from database
     * Calculate its eligibility for investments
     * @param userId
     * @return true if the user is eligible for investments, false otherwise
     */
    public boolean checkInvestmentEligibility(String userId) {
        UserProfile temp = null;
        for (UserProfile up : cache) {
            if (up.getUserId() == userId) {
                temp = up;
            }
        }
        if (temp == null) {
            temp = userProfileService.findByUserId(userId);
            cache.add(temp);
        }

        try {
            if (temp.getUserPersonalInformation().getAge() > 65 && temp.getUserIncomeInformation().getDepositBalanceInUSD() >= 1_000_000f) {
                return true;
            } else if (temp.getUserPersonalInformation().getAge() <= 65) {
                if (temp.getUserIncomeInformation().getDepositBalanceInUSD() >= 500_000f) {
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
