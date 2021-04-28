package com.skyvolt.jabber;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import client.data.DummyData;
import client.ui.GotoActivity;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;
import shared.BaseActivity;

public class LoginOrSignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login or Sign Up");
        setContentView(contentView());
    }

    private View contentView() {
        LinearLayout view = MakeDummy.linearLayoutVertical(
                this,
                image(),
                vspaceNormal(),
                title(),
                subtitle(),
                vspaceNormal(),
                login(),
                vspaceNormal(),
                signup()
        );
        view.setPadding(Dp.scaleBy(3), Dp.scaleBy(3), Dp.scaleBy(3), Dp.scaleBy(3));
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        view.setBackgroundColor(Color.WHITE);
        return view;
    }


    private View title() {
        return MakeDummy.wrapContent(Atom.textViewPrimaryBold(this, "<big>Welcome !!</big>"));
    }

    private View subtitle() {
        return MakeDummy.wrapContent(Atom.textViewPrimaryNormal(this, "<small>Create an account or login to get started</small>"));
    }


    private View image() {
        return MakeDummy.wrapContent(
                MakeDummy.linearLayoutVertical(
                        this,
                        DummyData.randomCircleImageView(this, Dp.scaleBy(8))
                )
        );
    }

    private View vspaceNormal() {
        return MakeDummy.lineSeparator(this, Dp.normal());
    }

    private View login() {
        return Atom.button(this, "Login", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.login(LoginOrSignUpActivity.this);
            }
        });
    }

    private View signup() {
        View view = Atom.button(this, "Create Account", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.signUp(LoginOrSignUpActivity.this);
            }
        });
        view.setBackgroundResource(R.drawable.button_accent);
        return view;
    }
}
