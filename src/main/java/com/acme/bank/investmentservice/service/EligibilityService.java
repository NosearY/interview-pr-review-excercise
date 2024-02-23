package com.acme.bank.investmentservice.service;

import com.acme.bank.investmentservice.domain.UserProfile;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Service
public class EligibilityService {

    public EligibilityService(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    private static final int AGE_TO_CONSIDER_ELDERLY = 65;

    private static final float DEPOSIT_BALANCE_REQUIREMENT_FOR_ELDERLY = 1_000_000f;
    private static final float DEPOSIT_BALANCE_REQUIREMENT_FOR_OTHERS = 500_000f;
    private final UserProfileService userProfileService;

    private final ConcurrentHashMap<String, UserProfile> userProfileCache = new ConcurrentHashMap<>();

    public boolean checkInvestmentEligibility(String userId) {
        UserProfile userProfile = userProfileCache.computeIfAbsent(userId, userProfileService::findByUserId);
        validateOrThrow(userProfile);

        return userIsElderlyAndWithSufficientDepositBalance(userProfile) ||
                userIsNotElderAndWithSufficientDepositBalance(userProfile);
    }

    private void validateOrThrow(UserProfile userProfile) {
        require(() -> userProfile != null, "UserProfile must not be null");
        require(() -> userProfile.getUserPersonalInformation() != null, "UserPersonalInformation must not be null");
        require(() -> userProfile.getUserPersonalInformation().getAge() > 0, "UserPersonalInformation.age musts be larger than 0");
        require(() -> userProfile.getUserIncomeInformation() != null, "UserIncomeInformation must not be null");
    }

    private boolean userIsElderlyAndWithSufficientDepositBalance(UserProfile userProfile) {
        return userProfile.getUserPersonalInformation().getAge() > AGE_TO_CONSIDER_ELDERLY &&
                userProfile.getUserIncomeInformation().getDepositBalanceInUSD() >= DEPOSIT_BALANCE_REQUIREMENT_FOR_ELDERLY;
    }

    private boolean userIsNotElderAndWithSufficientDepositBalance(UserProfile userProfile) {
        return userProfile.getUserPersonalInformation().getAge() <= 65 &&
                userProfile.getUserIncomeInformation().getDepositBalanceInUSD() >= DEPOSIT_BALANCE_REQUIREMENT_FOR_OTHERS;
    }

    private void require(Supplier<Boolean> predicate, String failureReason) {
        if (!predicate.get()) {
            throw new IllegalArgumentException(failureReason);
        }
    }
}
