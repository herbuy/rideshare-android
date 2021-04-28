package client.ui.display.family;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.skyvolt.jabber.R;

import client.ui.IntentExtras;
import core.businessmessages.toServer.ParamsForSendFamilyMessage;
import core.businessobjects.Family;
import libraries.JsonEncoder;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.RelativeLayoutFactory;
import shared.BaseActivity;

//represents a chat between logged in user and their family
public class FamilyChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renderActivity();


    }

    private void renderActivity() {
        setTitle("Family Chat");

        final Family familyOrMarriage = JsonEncoder.decode(getIntent().getStringExtra(IntentExtras.family), Family.class);
        if(familyOrMarriage == null){
            MessageBox.show("family not specified", this);
        }
        else{
            familyMessageList = new FamilyMessageList(FamilyChatActivity.this,familyOrMarriage);
           setContentView(contentView(familyOrMarriage));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        familyMessageList.setStatusToStopped();
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        familyMessageList.setIsVisibleToTrue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        familyMessageList.setStatusToDestroyed();




    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        familyMessageList.setStatusToPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        familyMessageList.setStatusToPaused();
    }

    @Override
    protected void onResume() {
        super.onResume();
        familyMessageList.setStatusToResumed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        familyMessageList.setStatusToStarted();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        familyMessageList.setStatusToRestarted();
    }

    private View contentView(Family familyOrMarriage) {
        RelativeLayout layout = RelativeLayoutFactory.alignBelowWidget(
                header(familyOrMarriage),
                RelativeLayoutFactory.alignAboveWidget(
                        messageList(familyOrMarriage),
                        messageInputArea(familyOrMarriage)
                )
        );

        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setBackgroundResource(R.drawable.bmp_chat_bg);

        //this wrapper is for setting a background color
        RelativeLayout finalBackground = new RelativeLayout(this);
        finalBackground.setBackgroundColor(Color.parseColor("#f8eeee"));
        finalBackground.addView(layout);


        return finalBackground;
    }

    private View messageInputArea(Family familyOrMarriage) {
        return new FamilyMessageInputProcess(this,familyOrMarriage){
            @Override
            protected void onBeginSendMessage(ParamsForSendFamilyMessage paramsForSendFamilyMessage) {
                familyMessageList.sendMessage(paramsForSendFamilyMessage);
            }
        }.getView();
    }

    private FamilyMessageList familyMessageList;
    private View messageList(Family familyOrMarriage) {

        return familyMessageList.getView();
    }

    private View header(Family familyOrMarriage) {
        View header = new FamilyChatHeader(this,familyOrMarriage).getView();
        header.setBackgroundResource(R.drawable.header_background);
        //header.setBackgroundResource(R.drawable.border_bottom);
        return header;
    }
}
