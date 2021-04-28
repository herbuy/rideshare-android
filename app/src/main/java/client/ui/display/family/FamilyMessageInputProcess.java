package client.ui.display.family;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.skyvolt.jabber.R;

import client.data.LocalSession;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForSendFamilyMessage;
import core.businessobjects.FamilyMessage;
import core.businessobjects.Family;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.SquaredImageVIew;
import libraries.android.TextChangedListener;
import libraries.android.HerbuyTransitionForAlpha;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;

abstract public class FamilyMessageInputProcess implements HerbuyView {
    Context context;
    Family familyOrMarriage;
    private RelativeLayout layout;

    public FamilyMessageInputProcess(Context context, Family familyOrMarriage) {
        this.context = context;
        this.familyOrMarriage = familyOrMarriage;
    }

    View sendButton;
    @Override
    public View getView() {
        layout = RelativeLayoutFactory.alignleftOfWidget(
                createTextbox(),
                MakeDummy.wrapContent(MakeDummy.paddingLeft(MakeDummy.linearLayoutVertical(context,createSendButton()),Dp.half_em()))
        );

        handlePossibilityOfTextExists();


        layout.setBackgroundResource(R.drawable.footer_background);
        layout.setPadding(Dp.normal(),Dp.scaleBy(0.5f), Dp.normal(), 0);
        return layout;
    }

    private void displaySendButton() {
        TransitionManager.beginDelayedTransition(layout, new HerbuyTransitionForAlpha());
        sendButton.setAlpha(1);
        sendButton.setClickable(true);

    }
    private void hideSendButton() {
        TransitionManager.beginDelayedTransition(layout, new HerbuyTransitionForAlpha());
        sendButton.setAlpha(0);
        sendButton.setClickable(false);

    }

    private boolean textExists() {
        return !editMessage.getText().toString().trim().equalsIgnoreCase("");
    }

    EditText editMessage;
    private View createTextbox() {
        editMessage = Atom.editText(context, "Type your Message", "", new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                handlePossibilityOfTextExists();
            }
        });


        editMessage.setBackgroundResource(0);
        editMessage.setOnFocusChangeListener(null);
        return editMessage;
    }

    private void handlePossibilityOfTextExists() {
        if (textExists()) {
            displaySendButton();
        } else {
            hideSendButton();
        }
    }


    private View createSendButton() {

        ImageView imageView = new SquaredImageVIew(context);//Atom.textViewPrimaryNormal(context,"Send");
        imageView.setLayoutParams(new ViewGroup.LayoutParams(Dp.scaleBy(3),Dp.scaleBy(3)));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(R.drawable.bmp_chat_send_msg);
        sendButton = imageView;

        //sendButton.setBackgroundResource(R.drawable.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParamsForSendFamilyMessage paramsForSendFamilyMessage = new ParamsForSendFamilyMessage();
                paramsForSendFamilyMessage.setSessionId(LocalSession.instance().getId());
                paramsForSendFamilyMessage.setFromUserId(LocalSession.instance().getUserId());
                paramsForSendFamilyMessage.setToFamilyId(familyOrMarriage.getFamilyId());

                paramsForSendFamilyMessage.setMessageType(FamilyMessage.Type.TEXT_PLAIN);
                paramsForSendFamilyMessage.setMessageText(editMessage.getText().toString().trim());

                editMessage.setText("");
                onBeginSendMessage(paramsForSendFamilyMessage);

            }
        });

        sendButton.setAlpha(0);
        sendButton.setClickable(false);
        //sendButton.setVisibility(View.INVISIBLE);
        return sendButton;
    }

    protected abstract void onBeginSendMessage(ParamsForSendFamilyMessage paramsForSendFamilyMessage);
}
