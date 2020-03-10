package com.asadchattha.tiktoklikesandfollowers.Model;

public class Follower {

    private String mDescription;
    private String mDiamondAmount;

    public Follower(String description, String diamondAmount) {
        mDescription = description;
        mDiamondAmount = diamondAmount;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getDiamondAmount() {
        return mDiamondAmount;
    }
}
