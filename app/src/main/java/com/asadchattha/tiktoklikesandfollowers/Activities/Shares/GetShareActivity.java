package com.asadchattha.tiktoklikesandfollowers.Activities.Shares;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.asadchattha.tiktoklikesandfollowers.Activities.Comments.GetCommentsActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.PurchaseReactionsActivity;
import com.asadchattha.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.asadchattha.tiktoklikesandfollowers.R;
import com.asadchattha.tiktoklikesandfollowers.Adapters.GetFollowerAdapter;
import com.asadchattha.tiktoklikesandfollowers.Model.Follower;

import java.util.ArrayList;

public class GetShareActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private SharedPrefrencesHelper sharedPrefrencesHelper;

    private String priceInDiamonds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        final ArrayList<Follower> follower = new ArrayList<Follower>();
        follower.add(new Follower("Get 20 real shares in 60 diamonds.", "Get 60 Shares", 60));
        follower.add(new Follower("Get 40 real shares in 100 diamonds.", " Get 100 Shares", 100));
        follower.add(new Follower("Get 80 real shares in 180 diamonds.", "Get 180 Shares", 180));
        follower.add(new Follower("Get 200 real shares in 400 diamonds.", "Get 400 Shares", 400));
        follower.add(new Follower("Get 500 real shares in 900 diamonds.", "Get 900 Shares", 900));
        follower.add(new Follower("Get 1000 real shares in 1600 diamonds.", "Get 1000 Shares", 1600));

        GetFollowerAdapter itemsAdapter = new GetFollowerAdapter(this, follower, R.drawable.ic_get_share);

        //Access LinearLayout to add subView into that.
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        /*SharedPref Helper*/
        sharedPrefrencesHelper = new SharedPrefrencesHelper(GetShareActivity.this);

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


        /*List Item Click Action*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the {@link Word} object at the given position the user clicked on
                Follower word = follower.get(position);

                priceInDiamonds = Integer.toString(word.getDiamondAmount());

                if (Integer.parseInt(sharedPrefrencesHelper.getDiamonds()) >= word.getDiamondAmount()) {
                    showDialogPositive();
                } else {
                    showDialogNegative();
                }
            }
        });
    }

    private void loadToolbarData() {
        updateUI(sharedPrefrencesHelper.getDiamonds());

    }

    private void updateUI(String savedDiamondsInSharedPref) {
        TextView diamond = toolbar.findViewById(R.id.text_view_diamonds);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);

        toolbarTitle.setText("Get Share");
        diamond.setText(savedDiamondsInSharedPref);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadToolbarData();
    }


    private void showDialogPositive() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GetShareActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure want to get Shares against diamonds.")
                .setTitle("Confirmation");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GetShareActivity.this, PurchaseReactionsActivity.class);
                intent.putExtra("ReactionType", "Share");
                intent.putExtra("PriceInDiamonds", priceInDiamonds);
                startActivity(intent);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(GetShareActivity.this, "Purchase cancelled.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }


    private void showDialogNegative() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GetShareActivity.this);
        builder.setCancelable(false);
        builder.setMessage("You are insufficient diamonds to purchase TikTok Shares.")
                .setTitle("Insufficient Diamonds");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
