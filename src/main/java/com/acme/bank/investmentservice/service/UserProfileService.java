package com.acme.bank.investmentservice.service;

import com.acme.bank.investmentservice.domain.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    public UserProfile findByUserId(String userId) {
        return new UserProfile();
    }
}
