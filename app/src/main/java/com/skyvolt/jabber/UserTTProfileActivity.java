package com.skyvolt.jabber;

import android.os.Bundle;

import client.ui.IntentExtras;
import client.ui.display.users.UserTTProfile;
import core.businessobjects.User;
import libraries.JsonEncoder;
import libraries.android.MessageBox;
import shared.BaseActivity;

public class UserTTProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = JsonEncoder.decode(getIntent().getStringExtra(IntentExtras.user), User.class);
        if(user == null){
            MessageBox.show("User not specified",this);
        }
        setTitle(user.getUserName() + ": "+user.getMobileNumber());
        setContentView(new UserTTProfile(this,user).getView());
    }
}
