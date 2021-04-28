package client.ui.display.users.chat;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.data.LocalSession;
import client.ui.GotoActivity;
import client.ui.libraries.HerbuyView;
import core.businessobjects.ChatMessage;
import core.businessobjects.Trip;
import libraries.JsonEncoder;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;
import resources.ItemColor;

public class MessageInterface implements HerbuyView {
    protected final Context context;
    protected final ChatMessage chatMessage;

    public MessageInterface(Context context, ChatMessage chatMessage) {
        this.context = context;
        this.chatMessage = chatMessage;
    }

    @Override
    public View getView() {
        LinearLayout marginContainer = MakeDummy.linearLayoutVertical(
                context,
                background()
        );

        marginContainer.setPadding(getMarginLeft(), Dp.scaleBy(0.5f), getMarginRight(), Dp.scaleBy(0.5f));
        if (messageIsFromMe()) {
            marginContainer.setGravity(Gravity.RIGHT);
        } else {
            marginContainer.setGravity(Gravity.LEFT);
        }
        return marginContainer;
    }

    private View background() {
        LinearLayout bg = MakeDummy.linearLayoutVertical(
                context,
                sender(),
                correlationBackground(),
                content(),
                time()
        );
        bg.setBackgroundResource(getBackgroundResource());
        bg.setPadding(Dp.scaleBy(1f), Dp.scaleBy(0.5f), Dp.scaleBy(1f), Dp.scaleBy(0.5f));
        MakeDummy.wrapContent(bg);
        return bg;
    }

    private View correlationBackground() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                correlationSender(),
                correlationDetails()
        );

        //make the background of the correlation content a little darker to differentiate it from the main content
        layout.setBackgroundResource(R.drawable.correlation_bg);

        layout.setPadding(Dp.scaleBy(1f), Dp.scaleBy(0.5f), Dp.scaleBy(1f), Dp.scaleBy(0.5f));
        layout.setVisibility(View.GONE);
        return layout;
    }

    private View correlationDetails() {
        return Atom.textViewPrimaryNormal(
                context,
                "Thanks"//"Thanks. These guys have played us"
        );
    }

    private View correlationSender() {
        return Atom.textViewPrimaryBold(context, "You");
    }

    private View sender() {
        return MakeDummy.paddingRight(MakeDummy.textColor(Atom.textViewPrimaryBold(context, chatMessage.getFromUserName()), ItemColor.primary()), Dp.normal());
    }

    private View time() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                MakeDummy.wrapContent(Atom.textViewSecondary(context, "18:45"))
        );
        layout.setGravity(Gravity.RIGHT);
        layout.setPadding(Dp.normal(), 0, Dp.normal(), 0);
        return layout;
    }

    protected View content() {
        switch (chatMessage.getMessageType()) {
            case ChatMessage.Type.TRAVELLING:
                return Text.travelling(context, chatMessage);
        };
        return Text.plain(context,chatMessage);
    }

    private int getBackgroundResource() {
        if (messageIsFromMe()) {

            return R.drawable.bg_msg_from_me;

        } else {
            return R.drawable.bg_msg_from_contact;
        }

    }

    private int getMarginRight() {
        if (messageIsFromMe()) {
            return Dp.scaleBy(1);
        } else {
            return Dp.scaleBy(8);
        }
    }

    private int getMarginLeft() {
        if (messageIsFromMe()) {
            return Dp.scaleBy(8);
        } else {
            return Dp.scaleBy(1);
        }
    }

    private boolean messageIsFromMe() {
        return chatMessage.isFromUser(LocalSession.instance().getUserId());
    }

    private static class Text {

        public static View plain(Context context, ChatMessage chatMessage) {
            return Atom.textViewPrimaryNormal(
                    context,
                    chatMessage.getText() //"I know"//"I know.. and somehow people just continue to beleive without questioning"
            );
        }

        public static View travelling(final Context context, ChatMessage chatMessage) {
            final Trip trip = JsonEncoder.decode(chatMessage.getText(), Trip.class);
            TextView textView = Atom.textViewPrimaryNormal(
                    context,
                    String.format(
                            "Travelling from <b>%s</b> to <b>%s</b> on %s",
                            trip.getOriginName(), trip.getDestinationName(), trip.getTripDate()
                    )
            );
            textView.setAlpha(0.8f);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GotoActivity.tripDetails(context,trip.getTripId());
                }
            });
            return textView;
        }
    }
}
