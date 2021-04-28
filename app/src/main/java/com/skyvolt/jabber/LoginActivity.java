package com.skyvolt.jabber;

import android.os.Bundle;

import client.ui.display.sessions.LoginController;
import shared.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Log In");
        setContentView(new LoginController(this).getView());
    }
}
