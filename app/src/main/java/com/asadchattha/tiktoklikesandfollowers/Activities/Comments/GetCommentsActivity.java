package com.asadchattha.tiktoklikesandfollowers.Activities.Comments;

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

import com.asadchattha.tiktoklikesandfollowers.Activities.Designing.PopupActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.ScratchAndWinActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.Likes.GetLikesActivity;
import com.asadchattha.tiktoklikesandfollowers.Activities.PurchaseReactionsActivity;
import com.asadchattha.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.asadchattha.tiktoklikesandfollowers.R;
import com.asadchattha.tiktoklikesandfollowers.Adapters.GetFollowerAdapter;
import com.asadchattha.tiktoklikesandfollowers.Model.Follower;

import java.util.ArrayList;

public class GetCommentsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private SharedPrefrencesHelper sharedPrefrencesHelper;

    private String priceInDiamonds;
    private String numberOfReactions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        final ArrayList<Follower> follower = new ArrayList<Follower>();
        follower.add(new Follower("Get 20 real comments in 60 diamonds.", "Get 20 Comments", 60, 20));
        follower.add(new Follower("Get 40 real comments in 100 diamonds.", " Get 40 Comments", 100, 40));
        follower.add(new Follower("Get 80 real comments in 180 diamonds.", "Get 80 Comments", 180, 80));
        follower.add(new Follower("Get 200 real comments in 400 diamonds.", "Get 200 Comments", 400, 200));
        follower.add(new Follower("Get 500 real comments in 900 diamonds.", "Get 500 Comments", 900, 500));
        follower.add(new Follower("Get 1000 real comments in 1600 diamonds.", "Get 1000 Comments", 1600, 1000));

        GetFollowerAdapter itemsAdapter = new GetFollowerAdapter(this, follower, R.drawable.ic_comment);

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

        /*List Item Click Action*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the {@link Word} object at the given position the user clicked on
                Follower word = follower.get(position);

                priceInDiamonds = Integer.toString(word.getDiamondAmount());
                numberOfReactions = Integer.toString(word.getNumberOfReactions());

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

        toolbarTitle.setText("Get Comments");
        diamond.setText(savedDiamondsInSharedPref);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadToolbarData();
    }


    private void showDialogPositive() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GetCommentsActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure want to get Comments against diamonds.")
                .setTitle("Confirmation");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GetCommentsActivity.this, PurchaseReactionsActivity.class);
                intent.putExtra("ReactionType", "Comments");
                intent.putExtra("PriceInDiamonds", priceInDiamonds);
                intent.putExtra("NumberOfReactions", numberOfReactions);
                startActivity(intent);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(GetCommentsActivity.this, "Purchase cancelled.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }


    private void showDialogNegative() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GetCommentsActivity.this);
        builder.setCancelable(false);
        builder.setMessage("You are insufficient diamonds to purchase TikTok Comments.")
                .setTitle("Insufficient Diamonds");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


}
