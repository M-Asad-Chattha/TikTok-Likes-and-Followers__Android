package com.asadchattha.tiktoklikesandfollowers.Helper;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

public class ProgressHUDHelper {

    private Context mContext;
    private KProgressHUD hud;

    public ProgressHUDHelper(Context context) {
        this.mContext = context;
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

    }

    public void showProgressHUD() {
        hud.show();
    }

    public void hideProgressHUD() {
        if (hud.isShowing()) {
            hud.dismiss();
        }
    }
}
