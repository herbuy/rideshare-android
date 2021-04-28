package com.skyvolt.jabber;

import android.os.Bundle;

import client.ui.display.users.UsersFromDifferentSources;
import libraries.android.MakeDummy;
import resources.Dp;
import shared.BaseActivity;

public class UsersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("People");
        setContentView(MakeDummy.padding(new UsersFromDifferentSources(this).getView(), Dp.two_em()));
    }
}
