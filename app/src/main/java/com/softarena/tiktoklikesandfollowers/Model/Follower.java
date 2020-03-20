package com.softarena.tiktoklikesandfollowers.Model;

public class Follower {

    private String mDescription;
    private String mAmountDescription;
    private int mAmount;
    private int mNumberOfReactions;

    public Follower(String description, String amountDescription, int amount, int numberOfReactions) {
        mDescription = description;
        mAmountDescription = amountDescription;
        mAmount = amount;
        mNumberOfReactions = numberOfReactions;
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

    public int getNumberOfReactions() {
        return mNumberOfReactions;
    }
}
