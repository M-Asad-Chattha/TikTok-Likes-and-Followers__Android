package com.asadchattha.tiktoklikesandfollowers.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.asadchattha.tiktoklikesandfollowers.MainActivity;
import com.asadchattha.tiktoklikesandfollowers.R;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class PurchaseReactionsActivity extends AppCompatActivity {

    SharedPrefrencesHelper sharedPrefrencesHelper;

    /*Strings sended by Intent*/
    String reactionTypeSendedByIntent;
    String priceInDiamondsSendedByIntent;
    String numberOfReactionsSendedByIntent;

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
        numberOfReactionsSendedByIntent = getIntent().getStringExtra("NumberOfReactions");

        Toast.makeText(this, priceInDiamondsSendedByIntent + " Amount of diamonds collected for Reaction: " + numberOfReactionsSendedByIntent, Toast.LENGTH_SHORT).show();


        /*GUI Views Initializing*/
        imageViewReaction_1 = findViewById(R.id.image_view_type_1);
        imageViewReaction_2 = findViewById(R.id.image_view_type_2);

        textViewTitleMain = findViewById(R.id.text_view_title_main);
        textViewPasteInstruction = findViewById(R.id.text_view_pasteInstruction);
        textViewInputTitle = findViewById(R.id.text_view_inputTitle);
        textViewVerify_title = findViewById(R.id.text_view_verify_title);
        textViewNote = findViewById(R.id.text_view_note);
        textViewTitleInvoice = findViewById(R.id.text_view_title_invoice);
        textViewAmount = findViewById(R.id.text_view_amount);

        editText = findViewById(R.id.edit_text);

        /*Load saved Data into Toolbar{link@SharedPrefrences}*/
        loadToolbarData();

        /*Load GUI Data*/
        loadGUIValues();

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

            textViewTitleMain.setText(R.string.followers_title_main);
            textViewPasteInstruction.setText(R.string.followers_pasteInstruction);
            textViewInputTitle.setText(R.string.followers_input_title);
            textViewVerify_title.setText(R.string.followers_verify_title);
            textViewNote.setText(R.string.followers_note);
            textViewTitleInvoice.setText("Get " + numberOfReactionsSendedByIntent + " real followers in " + priceInDiamondsSendedByIntent + " diamonds");
            textViewAmount.setText(priceInDiamondsSendedByIntent);

            editText.setHint(R.string.followers_input_hint);

        } else if (reactionTypeSendedByIntent.equals("Likes")) {
            imageViewReaction_1.setImageResource(R.drawable.ic_tiktok);
            imageViewReaction_2.setImageResource(R.drawable.ic_tiktok);

            textViewTitleMain.setText(R.string.likes_title_main);
            textViewPasteInstruction.setText(R.string.likes_pasteInstruction);
            textViewInputTitle.setText(R.string.likes_input_title);
            textViewVerify_title.setText(R.string.likes_verify_title);
            textViewNote.setText(R.string.likes_note);
            textViewTitleInvoice.setText("Get " + numberOfReactionsSendedByIntent + " real likes in " + priceInDiamondsSendedByIntent + " diamonds");
            textViewAmount.setText(priceInDiamondsSendedByIntent);

            editText.setHint(R.string.likes_input_hint);

        } else if (reactionTypeSendedByIntent.equals("Comments")) {
            imageViewReaction_1.setImageResource(R.drawable.ic_tiktok);
            imageViewReaction_2.setImageResource(R.drawable.ic_tiktok);

            textViewTitleMain.setText(R.string.comments_title_main);
            textViewPasteInstruction.setText(R.string.comments_pasteInstruction);
            textViewInputTitle.setText(R.string.comments_input_title);
            textViewVerify_title.setText(R.string.comments_verify_title);
            textViewNote.setText(R.string.comments_note);
            textViewTitleInvoice.setText("Get " + numberOfReactionsSendedByIntent + " real comments in " + priceInDiamondsSendedByIntent + " diamonds");
            textViewAmount.setText(priceInDiamondsSendedByIntent);

            editText.setHint(R.string.comments_input_hint);

        } else if (reactionTypeSendedByIntent.equals("Shares")) {
            imageViewReaction_1.setImageResource(R.drawable.ic_tiktok);
            imageViewReaction_2.setImageResource(R.drawable.ic_tiktok);

            textViewTitleMain.setText(R.string.shares_title_main);
            textViewPasteInstruction.setText(R.string.shares_pasteInstruction);
            textViewInputTitle.setText(R.string.shares_input_title);
            textViewVerify_title.setText(R.string.shares_verify_title);
            textViewNote.setText(R.string.shares_note);
            textViewTitleInvoice.setText("Get " + numberOfReactionsSendedByIntent + " real shares in " + priceInDiamondsSendedByIntent + " diamonds");
            textViewAmount.setText(priceInDiamondsSendedByIntent);

            editText.setHint(R.string.shares_input_hint);
        }

    }

    public void onClickSubmit(View view) {
        validateInput(editText.getText().toString());
    }


    private void validateInput(String tiktokURL) {

        String patternStr = "https://vm.tiktok.com/+[a-zA-z0-9_-]+/";
        Pattern pattern = Pattern.compile(patternStr);

        // create a matcher that will match the given input against this pattern
        Matcher matcher = pattern.matcher(tiktokURL);

        boolean matchFound = matcher.matches();

        if (matchFound) {
            showProgressHUD();
        } else {
            showDialog();
        }
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Invalid URL, Try again with real URL.")
                .setTitle("Error");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}
