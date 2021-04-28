package com.skyvolt.jabber;

import android.os.Bundle;

import client.ui.IntentExtras;
import client.ui.display.login_details.update.LDUpdateController;
import core.businessobjects.UserLoginDetails;
import libraries.JsonEncoder;
import libraries.android.MessageBox;
import shared.BaseActivity;

public class LDUpdateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Update Login Details");

        UserLoginDetails userLoginDetails = JsonEncoder.decode(
                getIntent().getStringExtra(IntentExtras.userLoginDetails),
                UserLoginDetails.class
        );
        if(userLoginDetails == null){
            MessageBox.show("Login details not specified", this);
        }

        setContentView(new LDUpdateController(this,userLoginDetails).getView());

    }
}
