package client.ui.display.users.chat;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest;
import client.ui.IntentExtras;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetChatMessages;
import core.businessmessages.toServer.ParamsForSendMessage;
import core.businessobjects.ChatMessage;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.HerbuyAbsListViewer;
import libraries.android.HerbuyStateView;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class ChatMessageList implements HerbuyView {
    private Context context;
    private IntentExtras.ChatParameters chatParameters;
    FrameLayoutBasedHerbuyStateView messageListView;
    HerbuyAbsListViewer<ChatMessage> listViewer;

    public ChatMessageList(Context context, final IntentExtras.ChatParameters chatParameters) {
        this.context = context;
        this.chatParameters = chatParameters;
        messageListView = new FrameLayoutBasedHerbuyStateView(context);
        listViewer = new HerbuyAbsListViewer<ChatMessage>(context) {
            @Override
            protected AbsListView createAbsListView(Context context) {
                return new ListView(context);
            }

            @Override
            protected View createItemView(Context context, ChatMessage item) {
                //if is from me, set margin left = 4em, margin right = 1em
                //else set margin left = 1em, margin right = 4em [getMarginLeft]
                //render message
                //render date time + status


                return new MessageInterface(context,item).getView();
                /*
                return MakeDummy.backgroundResource(
                        MakeDummy.padding(
                                MakeDummy.textView(context,item.getText()),Dp.normal()
                        ), R.drawable.chat_edit_text
                );*/
            }
        };
        refresh();
    }

    public void refresh() {
        messageListView.render(busy());
        Rest.api().getChatMessages(paramsForGetChatMessages()).enqueue(new AppCallback<List<ChatMessage>>() {
            @Override
            protected void onSuccess(Call<List<ChatMessage>> call, Response<List<ChatMessage>> response) {
                if (response.body() == null) {
                    messageListView.render(emptyBody());
                } else {
                    messageListView.render(success(response.body()));
                }

            }

            @Override
            protected void onError(Call<List<ChatMessage>> call, Throwable t) {
                messageListView.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        refresh();
                    }
                });
            }
        });
    }

    private View success(List<ChatMessage> body) {
        if(body.size() < 1){
            return emptyBody();
        }
        else{
            return listOfMessages(body);
        }
    }

    private View listOfMessages(List<ChatMessage> body) {

        listViewer.setContent(body);
        return listViewer.getView();
    }

    private View emptyBody() {
        TextView item = Atom.textView(context, "No Messages. Type and send a message to " + chatParameters.getTargetUserName());
        item.setPadding(Dp.four_em(),Dp.four_em(),Dp.four_em(),Dp.four_em());
        item.setBackgroundColor(Color.WHITE);

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(context,item);
        wrapper.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());


        return MakeDummy.linearLayoutVertical(
                context,
                wrapper,
                listViewer.getView()

        );
    }

    private ParamsForGetChatMessages paramsForGetChatMessages() {
        ParamsForGetChatMessages paramsForGetChatMessages = new ParamsForGetChatMessages();
        paramsForGetChatMessages.setSessionId(LocalSession.instance().getId());
        paramsForGetChatMessages.setForUserId(LocalSession.instance().getUserId());
        paramsForGetChatMessages.setContactId(chatParameters.getTargetUserId());
        return paramsForGetChatMessages;
    }

    private View busy() {
        return MakeDummy.textView(context,"Loading");
    }

    @Override
    public View getView() {
        return messageListView.getView();
    }

    public void add(ParamsForSendMessage paramsForSendMessage, IntentExtras.ChatParameters chatParameters) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setFromUserId(LocalSession.instance().getUserId());

        chatMessage.setToUserId(chatParameters.getTargetUserId());
        chatMessage.setToUserName(chatParameters.getTargetUserName());
        chatMessage.setToUserProfilePic(chatParameters.getTargetUserName());

        chatMessage.setMessageType(paramsForSendMessage.getContentType());
        chatMessage.setText(paramsForSendMessage.getContent());

        listViewer.add(chatMessage);
    }
}
