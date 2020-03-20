package com.softarena.tiktoklikesandfollowers.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softarena.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.softarena.tiktoklikesandfollowers.Model.User;
import com.softarena.tiktoklikesandfollowers.R;
import com.facebook.ads.*;


public class HomeFragment extends Fragment {

    private AdView bannerAdView;
    private User mUser;

    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = mInflater.inflate(R.layout.fragment_home, container, false);


        AudienceNetworkAds.initialize(getContext());
        bannerAdView = new AdView(getContext(), getString(R.string.id_ad_banner), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = root.findViewById(R.id.banner_container_home);
        adContainer.addView(bannerAdView);
        bannerAdView.loadAd();
        AdSettings.setTestMode(true);

//        readFirebaseData();
        updateProfileDetail(root);

        return root;
    }


    private void updateProfileDetail(View view) {
        SharedPrefrencesHelper sharedPrefrencesHelper = new SharedPrefrencesHelper(getContext());
        TextView userName = view.findViewById(R.id.text_view_userName);
        userName.setText(sharedPrefrencesHelper.getUserName());
    }

    private void loadDataFromSharedPref(String tiktokURL, String tiktokUserKey) {
        SharedPreferences sharedpreferences = getContext().
                getSharedPreferences("TikTokLikesandShares", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("userkey", tiktokUserKey);
        editor.putString("profileURL", tiktokURL);
        editor.commit();

    }

}
