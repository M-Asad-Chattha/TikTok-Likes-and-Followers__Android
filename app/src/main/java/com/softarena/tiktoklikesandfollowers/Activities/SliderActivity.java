package com.softarena.tiktoklikesandfollowers.Activities;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.softarena.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.softarena.tiktoklikesandfollowers.HomeActivity;
import com.softarena.tiktoklikesandfollowers.MainActivity;
import com.softarena.tiktoklikesandfollowers.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.kaopiz.kprogresshud.KProgressHUD;

public class SliderActivity extends AppIntro {

    private SharedPrefrencesHelper sharedPrefrencesHelper;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_slider);


        sharedPrefrencesHelper = new SharedPrefrencesHelper(SliderActivity.this);
        if (!sharedPrefrencesHelper.isOpeningFirstTime()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {

            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f);

            setBarColor(Color.parseColor("#00F70101"));
            setSeparatorColor(Color.parseColor("#2196F3"));


            showIntroSlides();

        }


    }


    private void showIntroSlides() {
        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle("Open TikTok");
        sliderPage1.setDescription("Launch TikTok App on your Phone's home screen.");
        sliderPage1.setImageDrawable(R.drawable.slider_1);
        sliderPage1.setBgColor(R.color.colorPrimary);


        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("Tap the profile icon");
        sliderPage2.setDescription("It's the person icon at the bottom-right corner of the screen, This displays a list of your videos.");
        sliderPage2.setImageDrawable(R.drawable.slider_2);
        sliderPage2.setBgColor(R.color.colorPrimary);

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle("Tap [...] three dots");
        sliderPage3.setDescription("It's in the top-right corner of your Profile. You can also find a sharing option near the bottom-right corner of any of your video.");
        sliderPage3.setImageDrawable(R.drawable.slider_3);
        sliderPage3.setBgColor(R.color.colorPrimary);

        SliderPage sliderPage4 = new SliderPage();
        sliderPage4.setTitle("Click Share Profile");
        sliderPage4.setDescription("In Account section tap on share profile Menu. Note: Further process will be start when you share your TikTok profile with our App.");
        sliderPage4.setImageDrawable(R.drawable.slider_4);
        sliderPage4.setBgColor(R.color.colorPrimary);

        SliderPage sliderPage5 = new SliderPage();
        sliderPage5.setTitle("Copy TikTok Profile URL");
        sliderPage5.setDescription("Copy and Paste your URL, then click on Next Button to register your profile with Our App.");
        sliderPage5.setImageDrawable(R.drawable.slider_5);
        sliderPage5.setBgColor(R.color.colorPrimary);


        addSlide(AppIntroFragment.newInstance(sliderPage1));
        addSlide(AppIntroFragment.newInstance(sliderPage2));
        addSlide(AppIntroFragment.newInstance(sliderPage3));
        addSlide(AppIntroFragment.newInstance(sliderPage4));
        addSlide(AppIntroFragment.newInstance(sliderPage5));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        hud.show();
        goToMainActivity();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        hud.show();
        goToMainActivity();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }


    private void goToMainActivity() {
        if (hud.isShowing()) {
            hud.dismiss();
        }
        Intent intent = new Intent(SliderActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
