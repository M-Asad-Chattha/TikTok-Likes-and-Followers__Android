package com.asadchattha.tiktoklikesandfollowers.Activities.Comments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.asadchattha.tiktoklikesandfollowers.Activities.Likes.GetLikesActivity;
import com.asadchattha.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.asadchattha.tiktoklikesandfollowers.R;
import com.asadchattha.tiktoklikesandfollowers.Adapters.GetFollowerAdapter;
import com.asadchattha.tiktoklikesandfollowers.Model.Follower;

import java.util.ArrayList;

public class GetCommentsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private SharedPrefrencesHelper sharedPrefrencesHelper;

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

        /*SharedPref Helper*/
        sharedPrefrencesHelper = new SharedPrefrencesHelper(GetCommentsActivity.this);

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
    }

    private void loadToolbarData() {
        updateUI(sharedPrefrencesHelper.getDiamonds());

    }

    private void updateUI(String savedDiamondsInSharedPref) {
        TextView diamond = toolbar.findViewById(R.id.text_view_diamonds);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);

        toolbarTitle.setText("Get Comments");
        diamond.setText(savedDiamondsInSharedPref);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadToolbarData();
    }

}
