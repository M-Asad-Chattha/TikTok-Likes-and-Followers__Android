package com.softarena.tiktoklikesandfollowers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.softarena.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.material.tabs.TabLayout;

import com.softarena.tiktoklikesandfollowers.Adapters.CategoryAdapter;
import com.kaopiz.kprogresshud.KProgressHUD;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private SharedPrefrencesHelper sharedPrefrencesHelper;

    private KProgressHUD hud;

    private int[] tabIcons = {
            R.drawable.ic_tab_home,
            R.drawable.ic_tab_diamond,
            R.drawable.ic_tab_man,
            R.drawable.ic_tab_heart,
            R.drawable.ic_tab_comment,
            R.drawable.ic_tab_share
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSharedPrefrencesData();

        sharedPrefrencesHelper = new SharedPrefrencesHelper(HomeActivity.this);


        Toolbar toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        tabLayout = findViewById(R.id.tab_layout);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        AudienceNetworkAds.initialize(this);

        ColorStateList colors;
        if (Build.VERSION.SDK_INT >= 23) {
            colors = getResources().getColorStateList(R.color.tabselected, getTheme());
        } else {
            colors = getResources().getColorStateList(R.color.tabselected);
        }

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable icon = tab.getIcon();

            if (icon != null) {
                icon = DrawableCompat.wrap(icon);
                DrawableCompat.setTintList(icon, colors);
            }
        }
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);
    }


    private void updateUI(String savedDiamondsInSharedPref) {
        Toolbar customToolbar = findViewById(R.id.toolbar);
        TextView diamond = customToolbar.findViewById(R.id.text_view_diamonds);
        diamond.setText(savedDiamondsInSharedPref);
    }


    /*private void readFirebaseData() {

        SharedPreferences prefs = getSharedPreferences("TikTokLikesandShares", Context.MODE_PRIVATE);
        String userKey = prefs.getString("userkey", null);

        Log.i("READ", "[HomeActivity] User Key is: "+ userKey);

        if (userKey != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userKey);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);
//                        updateUI(user);

                        Log.i("READ", "Diamonds are: " + user.getDiamond());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            // User Key is invalid
        }
    }*/


    private void getSharedPrefrencesData() {
        SharedPreferences prefs = getSharedPreferences("TikTokLikesandShares", Context.MODE_PRIVATE);
        String diamonds = prefs.getString("diamonds", "0");

        Log.i("READ", " Saved Diamonds on Firebase in HomeActivity are: " + diamonds);
        updateUI(diamonds);
    }


    @Override
    protected void onResume() {
        super.onResume();

        updateUI(sharedPrefrencesHelper.getDiamonds());
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("If you are happy with our APP, Rate US OR EXIT")
                .setTitle("Confirmation");
        builder.setPositiveButton("Rate US", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.asadchattha.tiktoklikesandfollowers")));
            }
        }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        builder.setCancelable(false);
        builder.create().show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        hud = KProgressHUD.create(HomeActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        signOut();
        return super.onOptionsItemSelected(item);
    }


    private void signOut() {
        SharedPreferences preferences = getSharedPreferences("TikTokLikesandShares", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

        if (hud.isShowing()) {
            hud.dismiss();
        }
    }
}
