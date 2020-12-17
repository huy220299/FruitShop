package com.example.nvhuy.navdrawer.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nvhuy.navdrawer.login.LoginTabFragment;
import com.example.nvhuy.navdrawer.login.SignupTabFragment;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalsTabs;
    public LoginAdapter(FragmentManager fm,Context context, int totalsTabs){
        super(fm);
        this.context = context;
        this.totalsTabs = totalsTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return loginTabFragment;

            case 1:
                SignupTabFragment signupTabFragment = new SignupTabFragment();

                return signupTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalsTabs;
    }
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Login";
            case 1:
                return "Sign up";
            default:
                return null;
        }
    }
}
