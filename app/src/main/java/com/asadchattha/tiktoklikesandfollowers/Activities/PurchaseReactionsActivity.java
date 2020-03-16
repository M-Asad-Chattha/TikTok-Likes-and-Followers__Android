package com.asadchattha.tiktoklikesandfollowers.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.asadchattha.tiktoklikesandfollowers.Activities.Earn.WatchVideosActivity;
import com.asadchattha.tiktoklikesandfollowers.Helper.FirebaseDatabaseHelper;
import com.asadchattha.tiktoklikesandfollowers.Helper.SharedPrefrencesHelper;
import com.asadchattha.tiktoklikesandfollowers.R;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;

import de.hdodenhof.circleimageview.CircleImageView;

public class PurchaseReactionsActivity extends AppCompatActivity {

    SharedPrefrencesHelper sharedPrefrencesHelper;
    /*Strings sended by Intent*/
    String reactionTypeSendedByIntent;
    String priceInDiamondsSendedByIntent;
    // Progress HUD
    private KProgressHUD hud;
    private FirebaseDatabase database;
    private Toolbar toolbar;
    /*GUI Views*/
    private CircleImageView imageViewReaction_1;
    private CircleImageView imageViewReaction_2;

    private TextView textViewTitleMain;
    private TextView textViewPasteInstruction;
    private TextView textViewInputTitle;
    private TextView textViewVerify_title;
    private TextView textViewNote;
    private TextView textViewTitleInvoice;
    private TextView textViewAmount;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_reactions);

        sharedPrefrencesHelper = new SharedPrefrencesHelper(PurchaseReactionsActivity.this);

        /* Custom Toolbar  */
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

        /*intialize intent*/
        reactionTypeSendedByIntent = getIntent().getStringExtra("ReactionType");
        priceInDiamondsSendedByIntent = getIntent().getStringExtra("PriceInDiamonds");

        Toast.makeText(this, "Amount of diamonds needs to be deducted: " + priceInDiamondsSendedByIntent, Toast.LENGTH_SHORT).show();

        /*Load saved Data into Toolbar{link@SharedPrefrences}*/
        loadToolbarData();

        /*GUI Views Initializing*/
        imageViewReaction_1 = findViewById(R.id.image_view_type_1);
        imageViewReaction_2 = findViewById(R.id.image_view_type_2);

        textViewTitleMain = findViewById(R.id.text_view_title_main);
//        textViewPasteInstruction = findViewById(R.id.text_view_pasteInstruction)
        textViewInputTitle = findViewById(R.id.text_view_inputTitle);
        textViewVerify_title = findViewById(R.id.text_view_verify_title);
        textViewNote = findViewById(R.id.text_view_note);
        textViewTitleInvoice = findViewById(R.id.text_view_title_invoice);
        textViewAmount = findViewById(R.id.text_view_amount);

        editText = findViewById(R.id.edit_text);

    }


    private void updateSharedPref() {
        int mDiamonds = Integer.parseInt(sharedPrefrencesHelper.getDiamonds());
        mDiamonds += 10;

        sharedPrefrencesHelper.updateDiamonds(Integer.toString(mDiamonds));

//        Log.i("UPDATE", "Updated diamonds are: " + sharedpreferences.getString("diamonds", "0"));

        // Update UI
        updateFirebase(Integer.toString(mDiamonds));
        updateUI(Integer.toString(mDiamonds));

    }

    private void updateUI(String savedDiamondsInSharedPref) {
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        TextView diamond = toolbar.findViewById(R.id.text_view_diamonds);

        toolbarTitle.setText("Get " + reactionTypeSendedByIntent);
        diamond.setText(savedDiamondsInSharedPref);


    }


    private void showProgressHUD() {
        hud = KProgressHUD.create(PurchaseReactionsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }


    private void dismissProgressHUD() {
        if (hud.isShowing()) {
            hud.dismiss();
        }
    }


    private void updateFirebase(String diamonds) {

        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper(PurchaseReactionsActivity.this);
        firebaseDatabaseHelper.updateDiamonds(diamonds);

    }

    private void loadToolbarData() {
        updateUI(sharedPrefrencesHelper.getDiamonds());

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadToolbarData();
    }


    private void loadGUIValues() {
        if (reactionTypeSendedByIntent.equals("Followers")) {
            imageViewReaction_1.setImageResource(R.drawable.ic_tiktok);
            imageViewReaction_2.setImageResource(R.drawable.ic_tiktok);

//            textViewTitleMain.setText(getText(R.string.));
//            textViewPasteInstruction = findViewById(R.id.text_view_pasteInstruction)
            textViewInputTitle = findViewById(R.id.text_view_inputTitle);
            textViewVerify_title = findViewById(R.id.text_view_verify_title);
            textViewNote = findViewById(R.id.text_view_note);
            textViewTitleInvoice = findViewById(R.id.text_view_title_invoice);
            textViewAmount = findViewById(R.id.text_view_amount);

            editText = findViewById(R.id.edit_text);
        } else if (reactionTypeSendedByIntent.equals("Likes")) {

        } else if (reactionTypeSendedByIntent.equals("Comments")) {

        } else if (reactionTypeSendedByIntent.equals("Shares")) {

        }

    }
}
