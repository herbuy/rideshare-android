package client.ui.display.users.chat;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.skyvolt.jabber.R;

import client.data.LocalSession;
import client.ui.IntentExtras;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForSendMessage;
import core.businessobjects.ChatMessage;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.TextChangedListener;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;

public abstract class MessageInputBox implements HerbuyView {
    Context context;
    IntentExtras.ChatParameters chatParameters;

    public MessageInputBox(Context context, IntentExtras.ChatParameters chatParameters) {
        this.context = context;
        this.chatParameters = chatParameters;
    }


    @Override
    public View getView() {

        final EditText editMessage = Atom.editText(context, "Type Message...", "", new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editMessage.setBackgroundResource(R.drawable.chat_edit_text);

        Button sendButton = Atom.button(context, "Send", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editMessage.getText().toString() == null || editMessage.getText().toString().trim().equalsIgnoreCase("")){
                    MessageBox.show("Type a message first",context);
                }
                else{
                    final ParamsForSendMessage paramsForSendMessage = new ParamsForSendMessage();
                    paramsForSendMessage.setSessionId(LocalSession.instance().getId());
                    paramsForSendMessage.setTargetUserId(chatParameters.getTargetUserId());
                    paramsForSendMessage.setContentType(ChatMessage.Type.TEXT_PLAIN);
                    paramsForSendMessage.setContent(editMessage.getText().toString());
                    editMessage.setText("");
                    onSendMessage(paramsForSendMessage);
                }


            }
        });

        return MakeDummy.padding(
                RelativeLayoutFactory.alignleftOfWidget(editMessage, sendButton),
                Dp.normal()
        );

        /*

        return MakeDummy.linearLayoutHorizontal(
                context,
                editMessage,
                sendButton
        );*/
    }

    protected abstract void onSendMessage(ParamsForSendMessage paramsForSendMessage);
}
