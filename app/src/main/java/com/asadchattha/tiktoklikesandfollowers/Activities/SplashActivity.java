package com.asadchattha.tiktoklikesandfollowers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.asadchattha.tiktoklikesandfollowers.MainActivity;
import com.asadchattha.tiktoklikesandfollowers.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, SliderActivity.class);
        startActivity(intent);
        finish();
    }
}
