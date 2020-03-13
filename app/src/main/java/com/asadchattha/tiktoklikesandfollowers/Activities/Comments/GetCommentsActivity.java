package com.asadchattha.tiktoklikesandfollowers.Activities.Comments;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.asadchattha.tiktoklikesandfollowers.R;
import com.asadchattha.tiktoklikesandfollowers.Adapters.GetFollowerAdapter;
import com.asadchattha.tiktoklikesandfollowers.Model.Follower;

import java.util.ArrayList;

public class GetCommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        final ArrayList<Follower> follower = new ArrayList<Follower>();
        follower.add(new Follower("Get 20 real comments in 60 diamonds.", "Get 60 Comments"));
        follower.add(new Follower("Get 40 real comments in 100 diamonds.", " Get 100 Comments"));
        follower.add(new Follower("Get 80 real comments in 180 diamonds.", "Get 180 Comments"));
        follower.add(new Follower("Get 200 real comments in 400 diamonds.", "Get 400 Comments"));
        follower.add(new Follower("Get 500 real comments in 900 diamonds.", "Get 900 Comments"));
        follower.add(new Follower("Get 1000 real comments in 1600 diamonds.", "Get 1000 Comments"));

        GetFollowerAdapter itemsAdapter = new GetFollowerAdapter(this, follower, R.drawable.ic_add_comment);

        //Access LinearLayout to add subView into that.
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
