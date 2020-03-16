package com.asadchattha.tiktoklikesandfollowers.Model;

public class Follower {

    private String mDescription;
    private String mAmountDescription;
    private int mAmount;

    public Follower(String description, String amountDescription, int amount) {
        mDescription = description;
        mAmountDescription = amountDescription;
        mAmount = amount;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAmountDescription() {
        return mAmountDescription;
    }

    public int getDiamondAmount() {
        return mAmount;
    }
}
