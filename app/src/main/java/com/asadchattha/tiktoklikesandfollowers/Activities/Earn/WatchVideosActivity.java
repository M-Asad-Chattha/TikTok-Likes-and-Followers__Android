package com.asadchattha.tiktoklikesandfollowers.Activities.Earn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.asadchattha.tiktoklikesandfollowers.Helper.FirebaseDatabaseHelper;
import com.asadchattha.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.asadchattha.tiktoklikesandfollowers.MainActivity;
import com.asadchattha.tiktoklikesandfollowers.Model.User;
import com.asadchattha.tiktoklikesandfollowers.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatchVideosActivity extends AppCompatActivity {

    /**
     * facebook Native ad
     */
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    private NativeAd nativeAd;

    //    Rewarded Video Add
    private RewardedVideoAd rewardedVideoAd;

    // Progress HUD
    private KProgressHUD hud;

    private FirebaseDatabase database;

    private SharedPrefrencesHelper sharedPrefrencesHelper;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_videos);

        /*SharedPref Helper*/
        sharedPrefrencesHelper = new SharedPrefrencesHelper(WatchVideosActivity.this);

        /* Custom Toolbar  */
        toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
//        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        /*Setup up button [Back Button on Roight of AppBar]*/
        toolbar.setNavigationIcon(R.drawable.ic_up_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Your code
                finish();
            }
        });


        /*Load saved Data into Toolbar{link@SharedPrefrences}*/
        loadToolbarData();

        AudienceNetworkAds.initialize(this);
        loadNativeAd();

    }


    void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(this, "VID_HD_9_16_39S_APP_INSTALL#YOUR_PLACEMENT_ID");

        nativeAd.setAdListener(new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        });

        // Request an ad
        nativeAd.loadAd();
    }

    private void inflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);
    }

    private void loadRewardedVideoAd() {
        rewardedVideoAd = new RewardedVideoAd(this, "VID_HD_9_16_39S_APP_INSTALL#YOUR_PLACEMENT_ID");
//        VID_HD_9_16_39S_APP_INSTALL#YOUR_PLACEMENT_ID
//        YOUR_PLACEMENT_ID


        rewardedVideoAd.setAdListener(new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                // Rewarded video ad failed to load
                Log.e("TAG", "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d("TAG", "Rewarded video ad is loaded and ready to be displayed!");

                dismissProgressHUD();
                rewardedVideoAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d("TAG", "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d("TAG", "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d("TAG", "Rewarded video completed!");

                // Give Reward to user
                updateSharedPref();

            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d("TAG", "Rewarded video ad closed!");
            }
        });

        rewardedVideoAd.loadAd();
    }


    public void onClickWatchVideo(View view) {
        showProgressHUD();
        loadRewardedVideoAd();
    }


    private void updateSharedPref() {
        SharedPrefrencesHelper sharedPrefrencesHelper = new SharedPrefrencesHelper(WatchVideosActivity.this);

        int mDiamonds = Integer.parseInt(sharedPrefrencesHelper.getDiamonds());
        mDiamonds += 10;

        sharedPrefrencesHelper.updateDiamonds(Integer.toString(mDiamonds));

//        Log.i("UPDATE", "Updated diamonds are: " + sharedpreferences.getString("diamonds", "0"));

        // Update UI
        updateFirebase(Integer.toString(mDiamonds));
        updateUI(Integer.toString(mDiamonds));

    }

    private void updateUI(String savedDiamondsInSharedPref) {
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        TextView diamond = toolbar.findViewById(R.id.text_view_diamonds);

        toolbarTitle.setText("Watch Video");
        diamond.setText(savedDiamondsInSharedPref);
    }


    private void showProgressHUD() {
        hud = KProgressHUD.create(WatchVideosActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                    .setLabel("Please wait")
//                    .setDetailsLabel("Downloading data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }


    private void dismissProgressHUD() {
        if (hud.isShowing()) {
            hud.dismiss();
        }
    }


    private void updateFirebase(String diamonds) {

        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper(WatchVideosActivity.this);
        firebaseDatabaseHelper.updateDiamonds(diamonds);

        /*DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("TAG_Firebase", "Data could not be saved " + databaseError.getMessage());
                } else {
                    Log.i("TAG_Firebase", "Data saved successfully.");
                }
            }
        };

        *//* Gey Random Generated Key  *//*
        String userKey = database.getReference().push().getKey();
        DatabaseReference databaseReference = database.getReference("Users").child(userKey);

        Map dataMap = new HashMap();
        dataMap.put("diamond", diamonds);

        // Save Data to Firebase
        databaseReference.updateChildren(dataMap, completionListener);*/


    }

    private void loadToolbarData() {
        updateUI(sharedPrefrencesHelper.getDiamonds());

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadToolbarData();
    }
}
