package client.ui.display;

import android.content.Context;
import android.view.View;

import client.ui.libraries.HerbuyView;
import core.businessobjects.ChatMessage;

public abstract class ChatMessageView implements HerbuyView{
    Context context;
    ChatMessage item;

    public ChatMessageView(Context context, ChatMessage item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public View getView() {
        return null;
    }

    protected abstract void onReplyMessage(ChatMessage chatMessage);
    protected abstract void onDeleteMessage(ChatMessage chatMessage);
}
