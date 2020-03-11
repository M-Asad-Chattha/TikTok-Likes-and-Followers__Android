package com.asadchattha.tiktoklikesandfollowers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.ads.*;

public class MainActivity extends AppCompatActivity {
    private AdView bannerAdView;
    private InterstitialAd interstitialAd;

    private InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
        @Override
        public void onInterstitialDisplayed(Ad ad) {
            // Interstitial ad displayed callback
            Log.e("TAG", "Interstitial ad displayed.");
        }

        @Override
        public void onInterstitialDismissed(Ad ad) {
            // Interstitial dismissed callback
            Log.e("TAG", "Interstitial ad dismissed.");
        }

        @Override
        public void onError(Ad ad, AdError adError) {
            // Ad error callback
            Log.e("TAG", "Interstitial ad failed to load: " + adError.getErrorMessage());
        }

        @Override
        public void onAdLoaded(Ad ad) {
            // Interstitial ad is loaded and ready to be displayed
            Log.d("TAG", "Interstitial ad is loaded and ready to be displayed!");
            // Show the ad
            interstitialAd.show();
        }

        @Override
        public void onAdClicked(Ad ad) {
            // Ad clicked callback
            Log.d("TAG", "Interstitial ad clicked!");
        }

        @Override
        public void onLoggingImpression(Ad ad) {
            // Ad impression logged callback
            Log.d("TAG", "Interstitial ad impression logged!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Facebook Adds Audience Network SDK
        AudienceNetworkAds.initialize(this);

        interstitialAd = new InterstitialAd(this, "YOUR_PLACEMENT_ID"); //2440420156058066_2444457038987711
        interstitialAd.setAdListener(interstitialAdListener);


//        AdSettings.addTestDevice("HASHED ID");
        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();

        /** Banner Ad */

        // Instantiate an AdView object.
        // NOTE: The placement ID from the Facebook Monetization Manager identifies your App.
        // To get test ads, add IMG_16_9_APP_INSTALL# to your placement id. Remove this when your app is ready to serve real ads.

        bannerAdView = new AdView(this, "YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
//        2440420156058066_2440446256055456
        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(bannerAdView);

        // Request an ad
        bannerAdView.loadAd();

    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        if (bannerAdView != null && interstitialAd != null) {
            bannerAdView.destroy();
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}
