package com.skyvolt.jabber;

import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import client.ui.GotoActivity;

public class OnHomeMenuItemSelected {
    public static void run(MenuItem item, AppCompatActivity sender){
        switch (item.getItemId()){
            case R.id.menu_item_admin:
                GotoActivity.adminPanel(sender);
                break;
            case R.id.menu_item_switch_account:
                GotoActivity.login(sender);
                break;

            case R.id.menu_item_create_account:
                GotoActivity.signUp(sender);
                break;


        }
    }
}
