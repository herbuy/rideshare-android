package client.ui.display;

import android.content.Context;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.ui.IntentExtras;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForSendMessage;
import core.businessobjects.ChatMessage;
import retrofit2.Call;
import retrofit2.Response;

public abstract class MessageInputView implements HerbuyView {
    private Context context;
    //the message to which we are replying, if any
    private ChatMessage correlationMessage;
    private IntentExtras.ChatParameters chatParameters;//gives us info on who we are chatting with

    public MessageInputView(Context context, IntentExtras.ChatParameters chatParameters) {
        this.context = context;
        this.chatParameters = chatParameters;
    }

    @Override
    public View getView() {
        return null;
    }

    public void setCorrelationMessage(ChatMessage correlationMessage) {
        this.correlationMessage = correlationMessage;
        redrawUI(); //shd draw the ui with the message to which we are replying displayed
    }

    private void redrawUI() {

    }

    //shd be called after sending message
    private final AppCallback<List<ChatMessage>> sendMessageCallback = new AppCallback<List<ChatMessage>>() {
        @Override
        protected void onSuccess(Call<List<ChatMessage>> call, Response<List<ChatMessage>> response) {
            //could clear the message box, among other things, and add the message to the list,
            // and resort the list if necessary, and scroll to bottom if necessary
            clearInputBox();
            displaySentMessage(response.body().get(0));
        }



        @Override
        protected void onError(Call<List<ChatMessage>> call, Throwable t) {

        }
    };

    public AppCallback<List<ChatMessage>> getSendMessageCallback() {
        return sendMessageCallback;
    }

    private void clearInputBox() {

    }

    private void displaySentMessage(ChatMessage chatMessage) {

    }

    protected abstract void onSendMessage(MessageInputView sender,ParamsForSendMessage paramsForSendMessage); //called when message needs to be sent after ebing built


}
