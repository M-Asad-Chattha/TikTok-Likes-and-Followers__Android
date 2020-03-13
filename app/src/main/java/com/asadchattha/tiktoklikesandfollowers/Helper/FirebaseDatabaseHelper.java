package com.asadchattha.tiktoklikesandfollowers.Helper;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class FirebaseDatabaseHelper {

    private FirebaseDatabase database;
    private SharedPrefrencesHelper sharedPrefrencesHelper;

    public FirebaseDatabaseHelper(Context context) {
        database = FirebaseDatabase.getInstance();
        sharedPrefrencesHelper = new SharedPrefrencesHelper(context);
    }

    public void updateDiamonds(String diamonds) {
        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("TAG_Firebase", "Data could not be saved " + databaseError.getMessage());
                } else {
                    Log.i("TAG_Firebase", "Data saved successfully.");
                }
            }
        };

        /* Gey Random Generated Key  */
        String userKey = sharedPrefrencesHelper.getUserKey();
        DatabaseReference databaseReference = database.getReference("Users").child(userKey);

        Map dataMap = new HashMap();
        dataMap.put("diamond", diamonds);

        // Save Data to Firebase
        databaseReference.updateChildren(dataMap, completionListener);


    }

    /*private void updateFirebase(String diamonds) {

        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("TAG_Firebase", "Data could not be saved " + databaseError.getMessage());
                } else {
                    Log.i("TAG_Firebase", "Data saved successfully.");
                }
            }
        };

        *//* Gey Random Generated Key  *//*
        String userKey = database.getReference().push().getKey();
        DatabaseReference databaseReference = database.getReference("Users").child(userKey);

        Map dataMap = new HashMap();
        dataMap.put("diamond", diamonds);

        // Save Data to Firebase
        databaseReference.updateChildren(dataMap, completionListener);

    }*/

}
