package client.ui.display.contacts;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import client.data.DummyData;
import client.ui.GotoActivity;
import client.ui.IntentExtras;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Contact;
import de.hdodenhof.circleimageview.CircleImageView;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;

public class ContactManager implements HerbuyView {
    private Context context;
    private Contact contact;

    public ContactManager(Context context, Contact contact) {
        this.context = context;
        this.contact = contact;
    }

    RelativeLayout wrapper;

    @Override
    public View getView() {
        CircleImageView circleImageView = DummyData.randomCircleImageView(context);


        wrapper = RelativeLayoutFactory.alignRightOfWidget(
                circleImageView,
                MakeDummy.linearLayoutVertical(
                        context,
                        Atom.textViewPrimaryBold(context, "  " + contact.getContactName()),
                        actions()
                )
        );

        wrapper.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());

        return wrapper;
    }

    private View actions() {
        Button chatButton = Atom.button(context, "Chat", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentExtras.ChatParameters chatParameters = new IntentExtras.ChatParameters();
                chatParameters.setTargetUserId(contact.getContactId());
                chatParameters.setTargetUserName(contact.getContactName());
                chatParameters.setTargetUserProfilePic(contact.getContactProfilePic());

                GotoActivity.chat(context,chatParameters);
            }
        });
        MakeDummy.wrapContent(chatButton);
        return chatButton;
    }
}
