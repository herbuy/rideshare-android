package client.ui.display.contacts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import client.data.AppCallback;
import client.data.DummyData;
import client.data.LocalSession;
import client.data.Rest;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForAddContacts;
import core.businessobjects.Contact;
import core.businessobjects.User;
import de.hdodenhof.circleimageview.CircleImageView;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class ContactToAdd implements HerbuyView {
    private Context context;
    private User user;
    private FrameLayoutBasedHerbuyStateView view;
    private TextView txtStatus;

    public ContactToAdd(Context context, User user) {
        this.context = context;
        this.user = user;
        view = new FrameLayoutBasedHerbuyStateView(context);
        txtStatus = new TextView(context);
        view.render(defaultView());


    }


    private View defaultView() {
        CircleImageView circleImageView = DummyData.randomCircleImageView(context);


        RelativeLayout wrapper = RelativeLayoutFactory.alignRightOfWidget(
                circleImageView,
                MakeDummy.linearLayoutVertical(
                        context,
                        Atom.textViewPrimaryBold(context, "  " + user.getUserName()),
                        txtStatus
                )
        );

        ViewGroup.LayoutParams params = circleImageView.getLayoutParams();
        params.width = Dp.scaleBy(6);
        params.height = Dp.scaleBy(6);
        circleImageView.requestLayout();

        wrapper.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());

        wrapper.setOnClickListener(addContactListener());
        return wrapper;
    }

    private View.OnClickListener addContactListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAddContact();
            }
        };
    }

    protected void tryAddContact() {

        txtStatus.setText("Adding contact...");
        Rest.api().addContact(paramsForAddContact()).enqueue(new AppCallback<List<Contact>>() {
            @Override
            protected void onSuccess(Call<List<Contact>> call, Response<List<Contact>> response) {
                txtStatus.setText("Added successfully!");
            }

            @Override
            protected void onError(Call<List<Contact>> call, Throwable t) {
                txtStatus.setText(t.getMessage());
            }
        });
    }


    private ParamsForAddContacts paramsForAddContact() {
        ParamsForAddContacts params = new ParamsForAddContacts();
        params.setSessionId(LocalSession.instance().getId());
        params.setToUserId(LocalSession.instance().getUserId());
        params.setContactId(user.getUserId());
        return params;
    }


    @Override
    public View getView() {
        return view.getView();
    }
}
