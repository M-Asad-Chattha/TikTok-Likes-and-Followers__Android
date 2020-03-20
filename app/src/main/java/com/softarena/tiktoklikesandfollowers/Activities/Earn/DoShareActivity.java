package com.softarena.tiktoklikesandfollowers.Activities.Earn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarena.tiktoklikesandfollowers.Adapters.PostAdapter;
import com.softarena.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.softarena.tiktoklikesandfollowers.Model.Post;
import com.softarena.tiktoklikesandfollowers.Model.PostViewer;
import com.softarena.tiktoklikesandfollowers.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoShareActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SharedPrefrencesHelper sharedPrefrencesHelper;

    /*facebook Native ad*/
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    private NativeAd nativeAd;

    /*RecyclerView*/
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> posts;

    /*Firebase*/
    private FirebaseDatabase database;
    private KProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_share);


        /*SharedPref Helper*/
        sharedPrefrencesHelper = new SharedPrefrencesHelper(DoShareActivity.this);

        /*Custom Toolbar*/
        toolbar = findViewById(R.id.toolbar);

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

        /*RecyclerView Initialization*/
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        /*Post List initialization*/
        posts = new ArrayList<>();

        /*Intialize Firebase Instance*/
        database = FirebaseDatabase.getInstance();

        /*Load Firebase Posts Data in Recycler View*/
        progressHUD = KProgressHUD.create(DoShareActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        readPosts();


        AudienceNetworkAds.initialize(this);
        loadNativeAd();
    }


    void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(this, getString(R.string.id_ad_native));

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

    private void loadToolbarData() {
        updateUI(sharedPrefrencesHelper.getDiamonds());

    }

    private void updateUI(String savedDiamondsInSharedPref) {
        TextView diamond = toolbar.findViewById(R.id.text_view_diamonds);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);

        toolbarTitle.setText("Do Share");
        diamond.setText(savedDiamondsInSharedPref);
    }

    private void readPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = new Post();

                    post = snapshot.getValue(Post.class);
                    String userid = sharedPrefrencesHelper.getUserKey();

                    if (!post.getUserKey().equals(userid)) {
                        int numberOfViews = Integer.parseInt(post.getNumberOfViews());
                        int viewsLimit = Integer.parseInt(post.getViewsLimit());

                        if (post.getPostType().equals("Shares") &&
                                (numberOfViews < viewsLimit) &&
                                post.getStatus().equals("Progress")) {
                            if (snapshot.child("PostViewer").exists()) {
                                for (DataSnapshot snapshot2 : snapshot.child("PostViewer").getChildren()) {
                                    PostViewer postViewer = snapshot2.getValue(PostViewer.class);
                                    String postuserId = postViewer.getUserId();
                                    if (!userid.equals(postuserId)) {
                                        posts.add(post);
                                    }

                                }
                            } else {
                                posts.add(post);
                            }
                        } else if (numberOfViews == viewsLimit) {
                            updatePostStatus(post.getKey());
                        }
                    }
                }

                progressHUD.dismiss();
                postAdapter = new PostAdapter(DoShareActivity.this, posts);

                recyclerView.setAdapter(postAdapter);

                /*if(!posts.isEmpty()){
                    layoutPlaceHolder.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    userAdapter = new ConveyanceAdapter(FindConveyanceActivity.this, mUsers);
                    recyclerView.setAdapter(userAdapter);
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    layoutPlaceHolder.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*Reading Data to Load in RecyclerView*/
//    private void readPosts() {
//        Toast.makeText(this, "Fetching Data...", Toast.LENGTH_SHORT).show();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
//
//        /*Query For */
//        Query query = reference.orderByChild("postType").equalTo("Shares");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                posts.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Post post = new Post();
//
//                    post = snapshot.getValue(Post.class);
//                    String userid = sharedPrefrencesHelper.getUserKey();
//
//                    if (!post.getUserKey().equals(userid)) {
//                        int numberOfViews = Integer.parseInt(post.getNumberOfViews());
//                        int viewsLimit = Integer.parseInt(post.getViewsLimit());
//
//                        if ((numberOfViews < viewsLimit)) {
//                            if (snapshot.child("PostViewer").exists()) {
//                                for (DataSnapshot snapshot2 : snapshot.child("PostViewer").getChildren()) {
//                                    PostViewer postViewer = snapshot2.getValue(PostViewer.class);
//                                    String postuserId = postViewer.getUserId();
//                                    if (!userid.equals(postuserId)) {
//                                        posts.add(post);
//                                    }
//
//                                }
//                            } else {
//                                posts.add(post);
//                            }
//                        }
//                    }
//                }
//
//                postAdapter = new PostAdapter(DoShareActivity.this, posts);
//
//                recyclerView.setAdapter(postAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//        /*reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                posts.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Post post = new Post();
//
//                    post = snapshot.getValue(Post.class);
//                    String userid = sharedPrefrencesHelper.getUserKey();
//
//                    if (!post.getUserKey().equals(userid)) {
//                        int numberOfViews = Integer.parseInt(post.getNumberOfViews());
//                        int viewsLimit = Integer.parseInt(post.getViewsLimit());
//
//                        if (post.getPostType().equals("Shares") && (numberOfViews < viewsLimit)) {
//                            if (snapshot.child("PostViewer").exists()) {
//                                for (DataSnapshot snapshot2 : snapshot.child("PostViewer").getChildren()) {
//                                    PostViewer postViewer = snapshot2.getValue(PostViewer.class);
//                                    String postuserId = postViewer.getUserId();
//                                    if (!userid.equals(postuserId)) {
//                                        posts.add(post);
//                                    }
//
//                                }
//                            } else {
//                                posts.add(post);
//                            }
//                        }
//                    }
//                }
//
//                postAdapter = new PostAdapter(DoShareActivity.this, posts);
//
//                recyclerView.setAdapter(postAdapter);
//
//                *//*if(!posts.isEmpty()){
//                    layoutPlaceHolder.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                    userAdapter = new ConveyanceAdapter(FindConveyanceActivity.this, mUsers);
//                    recyclerView.setAdapter(userAdapter);
//                }
//                else {
//                    recyclerView.setVisibility(View.GONE);
//                    layoutPlaceHolder.setVisibility(View.VISIBLE);
//                }*//*
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });*/
//    }

    private void updatePostStatus(final String key) {
        final DatabaseReference databaseReference = database.getReference("Posts");
        Query query = databaseReference.child(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map dataMap = new HashMap();
                dataMap.put("status", "completed");
                databaseReference.child(key).updateChildren(dataMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadToolbarData();
    }
}
