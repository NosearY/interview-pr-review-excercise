package com.acme.bank.investmentservice.domain;

public class UserProfile {

    private String userId;
    private UserIncomeInformation userIncomeInformation;
    private UserPersonalInformation userPersonalInformation;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserPersonalInformation getUserPersonalInformation() {
        return userPersonalInformation;
    }

    public void setUserPersonalInformation(UserPersonalInformation userPersonalInformation) {
        this.userPersonalInformation = userPersonalInformation;
    }

    public UserIncomeInformation getUserIncomeInformation() {
        return userIncomeInformation;
    }

    public void setUserIncomeInformation(UserIncomeInformation userIncomeInformation) {
        this.userIncomeInformation = userIncomeInformation;
    }

    public static class UserIncomeInformation {

        private double depositBalanceInUSD;

        public double getDepositBalanceInUSD() {
            return depositBalanceInUSD;
        }

        public void setDepositBalanceInUSD(double depositBalanceInUSD) {
            this.depositBalanceInUSD = depositBalanceInUSD;
        }
    }

    public static class UserPersonalInformation {
        private int Age;

        public int getAge() {
            return Age;
        }

        public void setAge(int age) {
            Age = age;
        }
    }
}


