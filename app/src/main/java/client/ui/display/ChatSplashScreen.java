package client.ui.display;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.data.LocalSession;
import client.ui.GotoActivity;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.SquaredImageVIew;
import resources.Dp;
import resources.ItemColor;
import shared.BaseActivity;

public class ChatSplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        setContentView(contentView());



        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(LocalSession.instance().exists()){
                    finish();
                    GotoActivity.tripList(ChatSplashScreen.this);
                }
                else{
                    finish();
                    GotoActivity.loginOrSignup(ChatSplashScreen.this);
                }
            }
        }, 5000);
    }

    private View contentView() {
        View title = title();
        View image = image();
        View description = description();

        LinearLayout layout = MakeDummy.linearLayoutVertical(
                this,
                title,
                image,
                description
        );
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        layout.setBackgroundColor(ItemColor.primary());
        layout.setBackgroundResource(R.drawable.splash_screen_bg);
        layout.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());

        image.setScaleX(0);
        image.setScaleY(0);
        image.animate().scaleX(1).setDuration(1000);
        image.animate().scaleY(1).setDuration(1000);

        return layout;
    }

    private View title() {
        TextView textView = Atom.textViewPrimaryBold(this,ChatSplashScreen.this.getString(R.string.app_name));
        textView.setTextColor(Color.WHITE);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(Dp.two_em());

        textView.setTranslationY(-200);
        textView.animate().setDuration(1000).translationY(0).setStartDelay(400);
        return textView;
    }

    private View image() {
        Context context = this;
        int width = (int)(Math.min(
                context.getResources().getDisplayMetrics().widthPixels,
                context.getResources().getDisplayMetrics().heightPixels
        ) * 0.667f);

        ImageView imageView = new SquaredImageVIew(context);
        imageView.setImageResource(R.drawable.img_sharing_ride_white);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(width, width));

        return imageView;
    }

    private View description() {
        TextView textView = Atom.textViewPrimaryBold(this, "<small>Travel with someone</small><br/>Share fuel costs");
        textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        textView.setTextSize(Dp.scaleBy(1.5f));
        textView.setTextColor(Color.WHITE);

        textView.setTranslationY(200);
        textView.animate().setDuration(1000).translationY(0).setStartDelay(500);
        return textView;
    }
}
