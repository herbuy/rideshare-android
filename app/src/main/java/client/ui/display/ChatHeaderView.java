package client.ui.display;

import android.content.Context;
import android.view.View;

import client.ui.IntentExtras;
import client.ui.libraries.HerbuyView;

//placed at the top of the chat messages, indicates who we are talking to
public class ChatHeaderView implements HerbuyView {
    Context context;
    IntentExtras.ChatParameters chatParameters;

    public ChatHeaderView(Context context, IntentExtras.ChatParameters chatParameters) {
        this.context = context;
        this.chatParameters = chatParameters;
    }

    @Override
    public View getView() {
        return null;
    }
}
