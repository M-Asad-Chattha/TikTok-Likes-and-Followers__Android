package com.asadchattha.tiktoklikesandfollowers.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPrefrencesHelper {

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private String userKey;
    private String diamonds;
    private Context mContext;
    private String profileURL;


    public SharedPrefrencesHelper(Context context) {
        this.mContext = context;
        sharedpreferences = mContext.getSharedPreferences("TikTokLikesandShares", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public String getUserKey() {
        userKey = sharedpreferences.getString("userkey", null);
        return userKey;
    }

    public String getDiamonds() {
        diamonds = sharedpreferences.getString("diamonds", "0");
        return diamonds;
    }

    public String getProfileURL() {
        profileURL = sharedpreferences.getString("profileURL", null);
        return profileURL;
    }


    public void updateDiamonds(String diamonds) {
        editor.putString("diamonds", diamonds);
        editor.apply();
    }

    public boolean isOpeningFirstTime() {
        boolean isOpeningFirstTime = sharedpreferences.getBoolean("isOpeningFirstTime", true);
        return isOpeningFirstTime;
    }


    public void setIsOpeningFirstTime(boolean status) {
        editor.putBoolean("isOpeningFirstTime", status);
        editor.apply();
    }




    /*private void saveDataToSharedPref(String tiktokUserKey, String tiktokURL, String diamonds) {
        SharedPreferences sharedpreferences = getSharedPreferences("TikTokLikesandShares", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("userkey", tiktokUserKey);
        editor.putString("profileURL", tiktokURL);
        editor.putString("diamonds", diamonds);
        editor.apply();

    }*/

}
