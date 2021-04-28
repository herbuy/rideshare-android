package client.ui.display.contacts;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import client.ui.GotoActivity;
import libraries.android.MakeDummy;
import libraries.android.ColorCalc;
import resources.ItemColor;
import shared.BaseActivity;

public class ContactsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Your Contacts");
        setContentView(contentView());
    }

    private View contentView() {
        return MakeDummy.linearLayoutVertical(
                this,
                header(),
                new ListOfMyContacts(this).getView()
        );

    }

    private View header() {
        Button btnAdd = MakeDummy.button(this, "+ Add Contact");
        btnAdd.setBackgroundColor(Color.TRANSPARENT);
        btnAdd.setTextColor(ItemColor.primary());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.addContacts(v.getContext());
            }
        });

        LinearLayout layout = MakeDummy.linearLayoutVertical(
                this,
                btnAdd
        );

        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setBackgroundColor(ColorCalc.mixColors(
                ItemColor.primary(),Color.WHITE,0.8f
        ));
        return layout;
    }
}
