package client.ui.display.family;

// package user
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;
import java.util.List;
import client.data.AppCallback;
import client.data.DummyData;
import client.data.LocalSession;

//

import client.data.Rest2;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.display.files.LoadImage;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForSendFamilyMessage;
import core.businessobjects.FamilyMessage;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.HerbuyCalendar;
import libraries.android.MinOfScreenWidthAndHeight;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class FamilyMessageView implements HerbuyView {
    Context context;
    FamilyMessage familyMessage;
    FrameLayoutBasedHerbuyStateView view;
    EventListener listener;


    public FamilyMessageView(Context context, FamilyMessage familyMessage, EventListener listener) {
        this.context = context;
        this.familyMessage = familyMessage;
        view = new FrameLayoutBasedHerbuyStateView(context);
        this.listener = listener;
    }
    public interface EventListener{
        void onMessageSent(FamilyMessageView sender, FamilyMessage message);
    }

    public View getView() {
        refreshView();
        return view.getView();
    }


    private View systemMessage() {
        TextView textView = Atom.textView(context,familyMessage.getMessageText());
        //textView.setBackgroundColor(ColorCalc.setSaturation(0.1f, ColorCalc.mixColors(ItemColor.primary(), Color.parseColor("#ffffff"), 0.9f)));
        textView.setBackgroundResource(R.drawable.system_message_bg);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setMaxWidth(MinOfScreenWidthAndHeight.oneHalf(context));
        textView.setTextSize(Dp.scaleBy(0.9f));
        textView.setPadding(Dp.scaleBy(0.5f),Dp.scaleBy(0.5f),Dp.scaleBy(0.5f),Dp.scaleBy(0.5f));

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(context,textView);
        wrapper.setGravity(Gravity.CENTER_HORIZONTAL);
        wrapper.setPadding(Dp.scaleBy(0.3f), Dp.scaleBy(0.3f), Dp.scaleBy(0.3f), Dp.scaleBy(0.3f));
        return wrapper;
    }

    private void sendMessageIfToBeSent() {
        if(familyMessage.isSending()){

            Rest2.api().sendFamilyMessage(paramsForSendFamilyMessage()).enqueue(new AppCallback<List<FamilyMessage>>() {
                @Override
                protected void onSuccess(Call<List<FamilyMessage>> call, Response<List<FamilyMessage>> response) {
                    if(response.body() != null && response.body().size() > 0){

                        familyMessage = response.body().get(0);
                        if(familyMessage.isSending()){
                            familyMessage.setStatusToSent();
                        }
                        refreshView();
                        notifyMessageSent();


                    }
                    else{
                        familyMessage.setStatusToFailed();
                        refreshView();
                    }
                }

                @Override
                protected void onError(Call<List<FamilyMessage>> call, Throwable t) {

                    MessageBox.show(t.getMessage(), context);
                    familyMessage.setStatusToFailed();
                    refreshView();
                }
            });

        }
    }

    private void notifyMessageSent() {
        if(listener != null){
            listener.onMessageSent(this,familyMessage);
        }
    }


    private ParamsForSendFamilyMessage paramsForSendFamilyMessage() {
        ParamsForSendFamilyMessage paramsForSendFamilyMessage = new ParamsForSendFamilyMessage();
        paramsForSendFamilyMessage.setMessageText(familyMessage.getMessageText());
        paramsForSendFamilyMessage.setMessageType(familyMessage.getMessageType());
        paramsForSendFamilyMessage.setSessionId(LocalSession.instance().getId());
        paramsForSendFamilyMessage.setToFamilyId(familyMessage.getToFamilyId());
        paramsForSendFamilyMessage.setFromUserId(familyMessage.getFromUserId());
        paramsForSendFamilyMessage.setCorrelationId(familyMessage.getCorrelationId());
        return paramsForSendFamilyMessage;
    }

    private View margin() {
        //return Atom.textView(context,"Message");
        LinearLayout margin = MakeDummy.linearLayoutHorizontal(
                context,
                profilePic(),
                //MakeDummy.backgroundColor(Atom.textView(context,"Message"), Color.WHITE)
                paddingBox()
        );
        margin.setPadding(marginLeft(), Dp.scaleBy(0.5f), marginRight(), Dp.scaleBy(0.5f));
        margin.setGravity(getMessageGravity());
        return margin;

    }

    private View profilePic() {
        if(messageIsFromMe()){
            return MakeDummy.invisible(Atom.textView(context,""));
        }
        else{
            ImageView senderPic = DummyData.randomCircleImageView(context);
            LoadImage.fromFileShortName(senderPic,familyMessage.getFromUserProfilePic());

            LinearLayout imageWrapper = MakeDummy.linearLayoutVertical(context);
            imageWrapper.setLayoutParams(new ViewGroup.LayoutParams(Dp.scaleBy(3),Dp.scaleBy(3)));
            imageWrapper.addView(senderPic);
            return imageWrapper;

        }


    }

    private int getMessageGravity() {
        return messageIsFromMe() ? Gravity.RIGHT : Gravity.LEFT;
    }

    private View paddingBox() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                senderAndMessage(),
                timeAndStatus()
        );
        layout.setBackgroundResource(getBackgroundResource());
        layout.setPadding(Dp.normal(), Dp.scaleBy(0.5f), Dp.normal(), Dp.scaleBy(0.5f));
        MakeDummy.wrapContent(layout);
        return layout;
    }

    private int getBackgroundResource() {
        if(messageIsFromMe()){
            return R.drawable.bg_msg_from_me;
        }
        else{
            return R.drawable.bg_msg_from_contact;
        }
    }

    private View timeAndStatus() {
        LinearLayout layout = MakeDummy.linearLayoutHorizontal(
                context,
                time(),
                MakeDummy.verticalSeparator(context,Dp.normal()),
                status()
        );
        layout.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return layout;
    }

    public void setStatusToSeen(){
        familyMessage.setStatus(FamilyMessage.Status.SEEN);
        refreshView();
    }

    private void refreshView() {
        if (familyMessage.isSystemMessage()) {
            view.render(systemMessage());
        }
        else{
            view.render(margin());

            sendMessageIfToBeSent();
        }
    }

    private View status() {
        int backgroundResource = 0;
        if(familyMessage.isSeen()){
            backgroundResource = R.drawable.ic_chat_status_seen;
        }
        else if(familyMessage.isDelivered()){
            backgroundResource = R.drawable.ic_chat_status_delivered;
        }
        else if(familyMessage.isSent()){
            backgroundResource = R.drawable.ic_chat_status_sent;
        }
        else {
            backgroundResource = 0;
        }

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(backgroundResource);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(Dp.one_point_5_em(), Dp.one_point_5_em()));

        View status = messageIsFromMe() ? imageView : invisibleTextView();
        return status;


    }

    private TextView invisibleTextView() {
        TextView statusView = Atom.textView(context, "");
        statusView.setVisibility(View.GONE);
        return statusView;
    }

    private View time() {
        HerbuyCalendar calendar = new HerbuyCalendar(familyMessage.getTimestampCreatedMillis());

        String dateString = calendar.getFriendlyDate();
        if(calendar.isToday()){
            dateString = ""; //"Today";// we want a shorter way to display date
        }
        else if(calendar.isYesterday()){
            dateString = "Yesterday";// we want a shorter way to display date
        }
        else if(calendar.isTomorrow()){
            dateString = "Tomorrow";// we want a shorter way to display date
        }

        String finalString = String.format(
                "%s %s",
                dateString,
                calendar.getTimeTwelveHour()
        );

        TextView view = Atom.textViewSecondary(context, finalString);
        view.setTextSize(Dp.scaleBy(0.8f));
        return MakeDummy.wrapContent(view);
    }

    private View senderAndMessage() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                previousMessage(),
                currentMessage()
        );
        return layout;
    }

    private View previousMessage() {
        View previousMessage = Atom.textView(context,"");
        previousMessage.setVisibility(View.GONE);
        return previousMessage;
    }

    private View currentMessage() {
        TextView message = Atom.textView(context,"Unsupported message type");
        switch(familyMessage.getMessageType()){
            case FamilyMessage.Type.TEXT_PLAIN:
                message = Atom.textViewPrimaryNormal(context,
                        String.format("<b>%s</b>. %s", senderName(), familyMessage.getMessageText())
                );
        }
        message.setTextSize(Dp.scaleBy(0.9f));
        return message;
    }

    private String senderName() {
        return messageIsFromMe() ? "You" : familyMessage.getFromUserName();
    }


    private int marginRight() {
        if(messageIsFromMe()){
            return Dp.scaleBy(1);
        }
        else{
            return Dp.scaleBy(4);
        }
    }

    private int marginLeft() {
        if(messageIsFromMe()){
            return Dp.scaleBy(4);
        }
        else{
            return Dp.scaleBy(1);
        }

    }

    private boolean messageIsFromMe() {
        return familyMessage.isFromUser(LocalSession.instance().getUserId());
    }
}
