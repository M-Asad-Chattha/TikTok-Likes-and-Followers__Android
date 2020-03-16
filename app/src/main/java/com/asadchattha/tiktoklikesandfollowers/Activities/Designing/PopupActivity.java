package com.asadchattha.tiktoklikesandfollowers.Activities.Designing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.ScratchAndWinActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.WatchVideosActivity;
import com.asadchattha.tiktoklikesandfollowers.Helper.FirebaseDatabaseHelper;
import com.asadchattha.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.asadchattha.tiktoklikesandfollowers.R;
import com.cooltechworks.views.ScratchImageView;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import java.util.Random;


public class PopupActivity extends AppCompatActivity {

    private final int random = new Random().nextInt(9);
    private final int LUCKY_NUMBER = 1;
    private InterstitialAd interstitialAd;
    private ScratchImageView scratchImageView;
    private String rewardAmount;
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
        setContentView(R.layout.activity_popup);

        //set popup window with side margins
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .7), (int) (height * .5));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

       /* // end popup
        lv_athletes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sessionManager.setSelectedAthlete(athleteslist.get(position));
                Intent intent = new Intent(PopupActivity.this, ScratchAndWinActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

        /*facebook InterstitialAd*/
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "YOUR_PLACEMENT_ID"); //2440420156058066_2444457038987711
        interstitialAd.setAdListener(interstitialAdListener);

        rewardAmount = getIntent().getStringExtra("Reward");

        scratchImageView = findViewById(R.id.image_view_scratch);
        scratchImageView.setRevealListener(new ScratchImageView.IRevealListener() {
            @Override
            public void onRevealed(ScratchImageView iv) {
//                Toast.makeText(PopupActivity.this, "Scrateched", Toast.LENGTH_SHORT).show();
                Log.i("Scratch", "Scratched Successfully");
                if (random == LUCKY_NUMBER) {
                    showDialogSuccess();
                    updateSharedPref();
                    Toast.makeText(PopupActivity.this, "Awarded", Toast.LENGTH_SHORT).show();
                } else {
                    showDialogFailed();
                    Toast.makeText(PopupActivity.this, "Try Again Scratching", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onRevealPercentChangedListener(ScratchImageView siv, float percent) {

            }
        });


    }

    private void showDialogFailed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PopupActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Try Again to Select Scartch Award.")
                .setTitle("Failed");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PopupActivity.this, ScratchAndWinActivity.class);
                startActivity(intent);
                interstitialAd.loadAd();

            }
        });
        builder.create().show();
    }

    private void showDialogSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PopupActivity.this);
        builder.setCancelable(false);
        builder.setMessage("You successfully won award.")
                .setTitle("Congratulations");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PopupActivity.this, PopupAdActivity.class);
                startActivity(intent);
//                interstitialAd.loadAd();

            }
        });
        builder.create().show();
    }


    private void updateSharedPref() {
        SharedPrefrencesHelper sharedPrefrencesHelper = new SharedPrefrencesHelper(PopupActivity.this);

        int mDiamonds = Integer.parseInt(sharedPrefrencesHelper.getDiamonds());
        mDiamonds += Integer.parseInt(getIntent().getStringExtra("Reward"));

        sharedPrefrencesHelper.updateDiamonds(Integer.toString(mDiamonds));

        Toast.makeText(this, "Updated diamonds are: " + sharedPrefrencesHelper.getDiamonds(), Toast.LENGTH_SHORT).show();

        // Update Data
        updateFirebase(Integer.toString(mDiamonds));
//        updateUI(Integer.toString(mDiamonds));

    }

    private void updateFirebase(String diamonds) {

        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper(PopupActivity.this);
        firebaseDatabaseHelper.updateDiamonds(diamonds);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
