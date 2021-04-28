package com.skyvolt.jabber;

import android.graphics.Color;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.HerbuyTransitionForWidthAndHeight;
import resources.Dp;
import shared.BaseActivity;

public class AdvertActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup container = contentView();
        setContentView(container);
        animate(container);
    }

    private void animate(final ViewGroup container) {

        ///TransitionManager.beginDelayedTransition(container, new HerbuyTransitionForWidth());
        needARide.setTranslationY(400);
        needARide.animate().setDuration(1500).translationY(0);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                takeABolt.animate().setDuration(1500).translationY(0);
            }
        }, 2500);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(container, new AutoTransition().setDuration(2000));
                needARide.setVisibility(View.GONE);
                //takeABolt.setVisibility(View.GONE);
            }
        }, 4500);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(container, new AutoTransition().setDuration(2000));
                enterYourDest.setVisibility(View.VISIBLE);
                //takeABolt.setVisibility(View.GONE);
            }
        }, 6000);


        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(container, new HerbuyTransitionForWidthAndHeight().setDuration(2000));
                HerbuyTransitionForWidthAndHeight.setNewValues(0,0,scene2);
                //scene2.setVisibility(View.GONE);
            }
        }, 8000);


    }

    View needARide;
    ViewGroup scene2;
    View takeABolt;
    View enterYourDest;
    private ViewGroup contentView() {
        needARide = needARide();
        takeABolt = takeABolt();
        enterYourDest = enterYourDest();

        scene2 = MakeDummy.linearLayoutHorizontal(
                this,
                takeABolt,
                enterYourDest

        );
        MakeDummy.wrapContent(scene2);

        LinearLayout container = MakeDummy.linearLayoutVertical(
                this,
                needARide,
                scene2

        );
        container.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.setBackgroundColor(Color.WHITE);
        return container;
    }

    private View enterYourDest() {
        TextView textView = Atom.textViewPrimaryNormal(this, "Enter your destination");
        MakeDummy.wrapContent(textView);
        textView.setTextSize(Dp.scaleBy(2));
        textView.setVisibility(View.GONE);
        return textView;
    }

    private View needARide() {
        TextView textView = Atom.textViewPrimaryNormal(this, "Need a Ride?");
        MakeDummy.wrapContent(textView);
        textView.setTextSize(Dp.scaleBy(2));
        return textView;
    }
    private View takeABolt() {
        TextView textView = Atom.textViewPrimaryNormal(this, "Take a Bolt!!");
        MakeDummy.wrapContent(textView);
        textView.setTextSize(Dp.scaleBy(2));
        textView.setTranslationY(this.getResources().getDisplayMetrics().heightPixels);
        return textView;
    }
}
