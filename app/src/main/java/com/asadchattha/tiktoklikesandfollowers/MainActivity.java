package com.asadchattha.tiktoklikesandfollowers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.asadchattha.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.asadchattha.tiktoklikesandfollowers.Model.User;
import com.facebook.ads.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private AdView bannerAdView;
    private InterstitialAd interstitialAd;
    private FirebaseDatabase database;

    EditText editTextUserName;

    // Progress HUD
    private KProgressHUD hud;

    //    Helper SharedPrefrences
    private SharedPrefrencesHelper sharedPrefrencesHelper;

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

        editTextUserName = findViewById(R.id.edit_text_tiktokUserName);

        sharedPrefrencesHelper = new SharedPrefrencesHelper(MainActivity.this);
        if (!sharedPrefrencesHelper.isOpeningFirstTime()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        // Initialize the Facebook Adds Audience Network SDK
        AudienceNetworkAds.initialize(this);

        interstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#"); //2440420156058066_2444457038987711
        interstitialAd.setAdListener(interstitialAdListener);


//        AdSettings.addTestDevice("HASHED ID");
        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();
        AdSettings.setTestMode(true);

        /** Banner Ad */

        // Instantiate an AdView object.
        // NOTE: The placement ID from the Facebook Monetization Manager identifies your App.
        // To get test ads, add IMG_16_9_APP_INSTALL# to your placement id. Remove this when your app is ready to serve real ads.

        bannerAdView = new AdView(this, "IMG_16_9_APP_INSTALL#", AdSize.BANNER_HEIGHT_50);
//        2440420156058066_2440446256055456
        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(bannerAdView);

        // Request an ad
        bannerAdView.loadAd();
        database = FirebaseDatabase.getInstance();

    }

    public void onClickNext(View view) {
        EditText etTiktokURL = findViewById(R.id.edit_text_tiktokURL);
        EditText etTiktokUserName = findViewById(R.id.edit_text_tiktokUserName);

        String tiktokProfileURL = etTiktokURL.getText().toString();
        String tiktokUserName = etTiktokUserName.getText().toString();

        validateInput(tiktokProfileURL, tiktokUserName);

    }


    private void uploadDataOnFirebase(String url) {
        // Write a message to the database


        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("TAG_Firebase", "Data could not be saved " + databaseError.getMessage());
                } else {
                    goToNextActivity();
                    Log.i("TAG_Firebase", "Data saved successfully.");
                }
            }
        };

        /* Gey Random Generated Key  */
        String userKey = database.getReference().push().getKey();
        DatabaseReference databaseReference = database.getReference("Users").child(userKey);

        // Prepare Data for Firebase
        User user = new User();
        user.setDiamond("0");
        user.setProfileURL(url);
        user.setUserName(editTextUserName.getText().toString());

        // Save Data to Firebase
        databaseReference.setValue(user, completionListener);

        // Save Data to SharedPref
        saveDataToSharedPref(userKey, url, editTextUserName.getText().toString(), "0");
    }

    @Override
    protected void onDestroy() {
        if (bannerAdView != null && interstitialAd != null) {
            bannerAdView.destroy();
            interstitialAd.destroy();
        }
        super.onDestroy();
    }


    private void validateInput(String tiktokURL, String titktokUserName) {

        String patternStr = "https://vm.tiktok.com/+[a-zA-z0-9_-]+/";
//        "[a-zA-Z0-9._-]+@hotmail.com"

        Pattern pattern = Pattern.compile(patternStr);

        // create a matcher that will match the given input against this pattern
        Matcher matcher = pattern.matcher(tiktokURL);

        boolean matchFound = matcher.matches();

        if (matchFound && !titktokUserName.isEmpty()) {
            isUserExist(tiktokURL);
            hud = KProgressHUD.create(MainActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Invalid TikTok inputs.")
                .setTitle("Try Again");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }


    private void saveDataToSharedPref(String tiktokUserKey, String tiktokURL, String tiktokUserName, String diamonds) {
        SharedPreferences sharedpreferences = getSharedPreferences("TikTokLikesandShares", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("userkey", tiktokUserKey);
        editor.putString("profileURL", tiktokURL);
        editor.putString("userName", tiktokUserName);
        editor.putString("diamonds", diamonds);
        editor.apply();

    }

    private boolean isUserExist(String tiktokUserURL) {
        final String mTiktokUserURL = tiktokUserURL;


        Query userProfileURLQuery = FirebaseDatabase.getInstance().getReference().child("Users")
                .orderByChild("profileURL").equalTo(tiktokUserURL);

        userProfileURLQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String saevedKey = dataSnapshot1.getKey();

//                        Toast.makeText(MainActivity.this, "User Already Exist", Toast.LENGTH_SHORT).show();

                        readDataFromFirebase(saevedKey, mTiktokUserURL);

                    }
                } else {
                    uploadDataOnFirebase(mTiktokUserURL);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return false;
    }


    private void readDataFromFirebase(final String userKey, final String tiktokURL) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userKey);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

//                saveDataToSharedPref(userKey, tiktokURL, editTextUserName.getText().toString(), user.getDiamond());
                saveDataToSharedPref(userKey, tiktokURL, user.getUserName(), user.getDiamond());
                goToNextActivity();
                Log.i("READ", "Saved Diamonds on Firebase are: " + user.getDiamond());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void goToNextActivity() {
        if (hud.isShowing()) {
            hud.dismiss();
            sharedPrefrencesHelper.setIsOpeningFirstTime(false);
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
