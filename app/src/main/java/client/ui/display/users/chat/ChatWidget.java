package client.ui.display.users.chat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Random;

import client.ui.IntentExtras;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForSendMessage;
import resources.ItemColor;

public class ChatWidget implements HerbuyView {
    Context context;
    IntentExtras.ChatParameters chatParameters;
    ChatMessageList chatMessageList;
    FrameLayoutBasedHerbuyStateView messageInputArea;

    public ChatWidget(Context context, IntentExtras.ChatParameters chatParameters) {
        this.context = context;
        this.chatParameters = chatParameters;

        chatMessageList = new ChatMessageList(context,chatParameters);
        messageInputArea = new FrameLayoutBasedHerbuyStateView(context);

        messageInputArea.getView().setId(new Random().nextInt());
        messageInputArea.getView().setLayoutParams(layoutParamsForMessageInputArea());
        chatMessageList.getView().setLayoutParams(layoutParamsForMessageArea());

        messageInputArea.render(messageInputAreaContent());


    }

    private View messageInputAreaContent() {
        return new MessageInputBox(context,chatParameters){
            @Override
            protected void onSendMessage(ParamsForSendMessage paramsForSendMessage) {
                chatMessageList.add(paramsForSendMessage, chatParameters);
                //MessageBox.show(paramsForSendMessage.getText(),context);
            }
        }.getView();
    }

    private ViewGroup.LayoutParams layoutParamsForMessageInputArea() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        return layoutParams;
    }

    private ViewGroup.LayoutParams layoutParamsForMessageArea() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ABOVE, messageInputArea.getView().getId());
        return layoutParams;
    }

    @Override
    public View getView() {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.addView(chatMessageList.getView());
        relativeLayout.addView(messageInputArea.getView());

        relativeLayout.setBackgroundColor(ItemColor.chatBg());
        return relativeLayout;
    }

}
