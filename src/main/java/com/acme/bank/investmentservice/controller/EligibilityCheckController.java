package com.acme.bank.investmentservice.controller;

import com.acme.bank.investmentservice.service.EligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EligibilityCheckController {

    @Autowired
    private EligibilityService eligibilityService;

    @GetMapping("/eligibility")
    public ResponseEntity<Boolean> checkInvestmentEligibility(String userId) {
        return ResponseEntity.ok(eligibilityService.checkInvestmentEligibility(userId));
    }
}
