package com.asadchattha.tiktoklikesandfollowers.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.DoCommentActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.DoFollowingActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.DoLikesActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.DoShareActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.RateUsActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.ScratchAndWinActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.WatchVideosActivity;
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
import com.facebook.ads.NativeBannerAd;

import java.util.ArrayList;
import java.util.List;


public class EarnFragment extends Fragment {

    private CardView mCardWatchVideos;
    private CardView mCardScratchAndWin;
    private CardView mCardDoLikes;
    private CardView mCardDoFollowing;
    private CardView mCardDoComment;
    private CardView mCardDoShare;
    private CardView mCardRateUs;

    /**
     * facebook Native ad
     */
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    private NativeAd nativeAd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_earn, container, false);

        /** Watch videos CardView */
        mCardWatchVideos = root.findViewById(R.id.card_watchVideos);
        mCardWatchVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WatchVideosActivity.class);
                startActivity(intent);
            }
        });


        /** Scratch and Win CardView */
        mCardScratchAndWin = root.findViewById(R.id.card_scratchAndWin);
        mCardScratchAndWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScratchAndWinActivity.class);
                startActivity(intent);
            }
        });


        /** Do Likes CardView */
        mCardDoLikes = root.findViewById(R.id.card_doLikes);
        mCardDoLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DoLikesActivity.class);
                startActivity(intent);
            }
        });

        /** Do Following CardView */
        mCardDoFollowing = root.findViewById(R.id.card_doFollowing);
        mCardDoFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DoFollowingActivity.class);
                startActivity(intent);
            }
        });


        /** Do Comment CardView */
        mCardDoComment = root.findViewById(R.id.card_doComment);
        mCardDoComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DoCommentActivity.class);
                startActivity(intent);
            }
        });


        /** Do Share CardView */
        mCardDoShare = root.findViewById(R.id.card_doShare);
        mCardDoShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DoShareActivity.class);
                startActivity(intent);
            }
        });


        /** Rate Us CardView */
        mCardRateUs = root.findViewById(R.id.card_rateUs);
        mCardRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RateUsActivity.class);
                startActivity(intent);
            }
        });

        AudienceNetworkAds.initialize(getContext());
        loadNativeAd(root);

        return root;
    }

    void loadNativeAd(final View root) {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(getContext(), "VID_HD_9_16_39S_APP_INSTALL#YOUR_PLACEMENT_ID");

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
                inflateAd(nativeAd, root, R.id.native_ad_container_1);
                inflateAd(nativeAd, root, R.id.native_ad_container_2);
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

    private void inflateAd(NativeAd nativeAd, View root, int nativeAdID) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = root.findViewById(nativeAdID);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = root.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(getContext(), nativeAd, nativeAdLayout);
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
}
