package com.softarena.tiktoklikesandfollowers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softarena.tiktoklikesandfollowers.Helper.FirebaseDatabaseHelper;
import com.softarena.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.softarena.tiktoklikesandfollowers.Model.Post;
import com.softarena.tiktoklikesandfollowers.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostAdapterFollower extends RecyclerView.Adapter<PostAdapterFollower.ViewHolder> {
    private Context mContext;
    private List<Post> posts;
    private FirebaseDatabase database;


    private SharedPrefrencesHelper sharedPrefrencesHelper;

    public PostAdapterFollower(Context context, List<Post> posts) {
        this.posts = posts;
        this.mContext = context;
        sharedPrefrencesHelper = new SharedPrefrencesHelper(context);
        database = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item_follower, parent, false);
        return new PostAdapterFollower.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Post post = posts.get(position);
        holder.url.setText(post.getUrl());


        // Show complete Roommate Info
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Gey Random Generated Key  */
                final DatabaseReference databaseReference = database.getReference("Posts");
                Query query = databaseReference.child("PostViewer").orderByChild(post.getKey());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            Map dataMap = new HashMap();
                            dataMap.put("userId", sharedPrefrencesHelper.getUserKey());

                            // Save Data to Firebase
                            databaseReference.child(post.getKey()).child("PostViewer").push().updateChildren(dataMap);

                            /*Open Tiktok App*/
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(posts.get(position).getUrl()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                                updateDatabase();
                                updatePostViews(posts.get(position).getKey(), posts.get(position).getNumberOfViews());

                                mContext.startActivity(intent);
                            } else {
                                Toast.makeText(mContext, "No Application Found to open this URL.", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }

    private void updatePostViews(final String key, final String numberOfViews) {

        final DatabaseReference databaseReference = database.getReference("Posts");
        Query query = databaseReference.child(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int mNumberOfViews = Integer.parseInt(numberOfViews);
                mNumberOfViews++;

                Map dataMap = new HashMap();
                dataMap.put("numberOfViews", Integer.toString(mNumberOfViews));
                databaseReference.child(key).updateChildren(dataMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateDatabase() {
        int savedDiamonds = Integer.parseInt(sharedPrefrencesHelper.getDiamonds());
        savedDiamonds += 5;

        /*UPDATE SharedPrefrences*/
        sharedPrefrencesHelper.updateDiamonds(Integer.toString(savedDiamonds));

        /*Update Firebase*/
        FirebaseDatabaseHelper databaseHelper = new FirebaseDatabaseHelper(mContext);
        databaseHelper.updateDiamonds(Integer.toString(savedDiamonds));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView url;

        public ViewHolder(View itemView) {
            super(itemView);

            url = itemView.findViewById(R.id.url);

        }
    }


}