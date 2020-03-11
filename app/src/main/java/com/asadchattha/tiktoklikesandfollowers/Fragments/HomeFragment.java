package com.asadchattha.tiktoklikesandfollowers.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asadchattha.tiktoklikesandfollowers.R;
import com.facebook.ads.*;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    /*private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    private NativeBannerAd nativeBannerAd;*/

    private AdView bannerAdView;


/*
    private NativeAdListener nativeAdListener = new NativeAdListener() {
        @Override
        public void onMediaDownloaded(Ad ad) {
            // Native ad finished downloading all assets
            Log.e("TAG", "Native ad finished downloading all assets.");
        }

        @Override
        public void onError(Ad ad, AdError adError) {
            // Native ad failed to load
            Log.e("TAG", "Native ad failed to load: " + adError.getErrorMessage());
        }

        @Override
        public void onAdLoaded(Ad ad) {
            // Native ad is loaded and ready to be displayed
            Log.d("TAG", "Native ad is loaded and ready to be displayed!");
        }

        @Override
        public void onAdClicked(Ad ad) {
            // Native ad clicked
            Log.d("TAG", "Native ad clicked!");
            if (nativeBannerAd == null || nativeBannerAd != ad) {
                return;
            }
            // Inflate Native Banner Ad into Container
            inflateAd(nativeBannerAd);
        }

        @Override
        public void onLoggingImpression(Ad ad) {
            // Native ad impression
            Log.d("TAG", "Native ad impression logged!");
        }
    };
*/


    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = mInflater.inflate(R.layout.fragment_home, container, false);

        /** Native Add */
        /*nativeAdLayout = root.findViewById(R.id.native_banner_ad_container);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_layout, nativeAdLayout, false);

        nativeBannerAd = new NativeBannerAd(getContext(), "2440420156058066_2440446256055456");
        nativeBannerAd.setAdListener(nativeAdListener);

        // Request an ad
        AdSettings.addTestDevice("HASHED ID");
        nativeBannerAd.loadAd();
*/

        AudienceNetworkAds.initialize(getContext());
        bannerAdView = new AdView(getContext(), "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = root.findViewById(R.id.banner_container_home);
        adContainer.addView(bannerAdView);
        bannerAdView.loadAd();


        return root;
    }


    /*private void inflateAd(NativeBannerAd nativeBannerAd) {
        // Unregister last ad
        nativeBannerAd.unregisterView();

        // Add the Ad view into the ad container.
//        nativeAdLayout = findViewById(R.id.native_banner_ad_container);
//        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
//        adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(getContext(), nativeBannerAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        AdIconView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }*/

}
