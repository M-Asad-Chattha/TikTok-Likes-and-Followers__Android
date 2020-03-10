package com.asadchattha.tiktoklikesandfollowers.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asadchattha.tiktoklikesandfollowers.Activities.FollowersListActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.GetFollowerActivity;
import com.asadchattha.tiktoklikesandfollowers.R;


public class FollowerFragment extends Fragment {

    private CardView mCardGetFollowers;
    private CardView mCardFollowersList;

    public FollowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_follower, container, false);

        /** Get Followers CardView */
        mCardGetFollowers = root.findViewById(R.id.card_getFollowers);
        mCardGetFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GetFollowerActivity.class);
                startActivity(intent);
            }
        });


        /** Followers List CardView */
        mCardFollowersList = root.findViewById(R.id.card_followersList);
        mCardFollowersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowersListActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

}
