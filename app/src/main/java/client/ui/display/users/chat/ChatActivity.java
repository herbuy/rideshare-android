package client.ui.display.users.chat;

import android.os.Bundle;
import android.view.View;

import client.ui.IntentExtras;
import libraries.JsonEncoder;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import shared.BaseActivity;

public class ChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());
    }

    private View contentView() {
        final IntentExtras.ChatParameters chatParameters = getChatParameters();
        if(chatParameters == null){
            return MakeDummy.textView(this,"Chat parameters most likely not specified");
        }

        else {
            setTitle(chatParameters.getTargetUserName());
            return new ChatWidget(this, chatParameters).getView();
        }

    }

    private IntentExtras.ChatParameters getChatParameters() {
        try{
            String strChatParameters = getIntent().getStringExtra(IntentExtras.chatParameters);
            IntentExtras.ChatParameters chatParameters = JsonEncoder.decode(strChatParameters,IntentExtras.ChatParameters.class);
            return chatParameters;
        }
        catch (Exception ex){
            MessageBox.show(ex.getMessage(),this);
            return null;
        }
    }
}
