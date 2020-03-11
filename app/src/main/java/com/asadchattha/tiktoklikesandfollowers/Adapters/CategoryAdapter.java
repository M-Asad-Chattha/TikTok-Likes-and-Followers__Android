package com.asadchattha.tiktoklikesandfollowers.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.asadchattha.tiktoklikesandfollowers.Fragments.CommentFragment;
import com.asadchattha.tiktoklikesandfollowers.Fragments.EarnFragment;
import com.asadchattha.tiktoklikesandfollowers.Fragments.FollowerFragment;
import com.asadchattha.tiktoklikesandfollowers.Fragments.HomeFragment;
import com.asadchattha.tiktoklikesandfollowers.Fragments.LikeFragment;
import com.asadchattha.tiktoklikesandfollowers.Fragments.ShareFragment;
import com.asadchattha.tiktoklikesandfollowers.R;

public class CategoryAdapter extends FragmentPagerAdapter {

    /** Context of the app */
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else if (position == 1) {
            return new EarnFragment();
        } else if (position == 2) {
            return new FollowerFragment();
        } else if (position == 3) {
            return new LikeFragment();
        } else if (position == 4){
            return new CommentFragment();
        } else {
            return new ShareFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 6;
    }

    /**
     *
     *Display Fragment name in TabLayout
     */

    /*@Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_home);
        } else if (position == 1) {
            return mContext.getString(R.string.category_earn);
        } else if (position == 2) {
            return mContext.getString(R.string.category_follower);
        } else if (position == 3) {
            return mContext.getString(R.string.category_like);
        }  else if (position == 4) {
            return mContext.getString(R.string.category_comment);
        } else {
            return mContext.getString(R.string.category_share);
        }
    }*/

}
