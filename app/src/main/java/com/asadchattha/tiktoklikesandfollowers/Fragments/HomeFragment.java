package com.asadchattha.tiktoklikesandfollowers.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asadchattha.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.asadchattha.tiktoklikesandfollowers.Model.User;
import com.asadchattha.tiktoklikesandfollowers.R;
import com.facebook.ads.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private AdView bannerAdView;
    private User mUser;

    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = mInflater.inflate(R.layout.fragment_home, container, false);


        AudienceNetworkAds.initialize(getContext());
        bannerAdView = new AdView(getContext(), getString(R.string.banner_ad_id), AdSize.BANNER_HEIGHT_50);
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
