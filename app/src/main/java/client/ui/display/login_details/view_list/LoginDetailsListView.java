package client.ui.display.login_details.view_list;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import core.businessobjects.UserLoginDetails;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.HerbuyAbsListViewer;
import resources.Dp;

public abstract class LoginDetailsListView extends HerbuyAbsListViewer<UserLoginDetails> {
    public LoginDetailsListView(Context context) {
        super(context);
    }

    @Override
    protected AbsListView createAbsListView(Context context) {
        return new ListView(context);
    }


    @Override
    protected View createItemView(final Context context, final UserLoginDetails item) {

        View itemView = MakeDummy.linearLayoutVertical(
                context,
                Atom.textViewPrimaryBold(context, item.getMobileNumber()),
                Atom.textViewPrimaryNormal(context, item.getPassword()),
                updateLoginDetails(context, item),
                MakeDummy.lineSeparator(context, Dp.normal()),
                new AccountSwitchController(context,item).getView()
        );
        itemView.setPadding(Dp.two_em(), Dp.normal(), Dp.two_em(), Dp.normal());
        return itemView;
    }

    private View updateLoginDetails(Context context, final UserLoginDetails item) {
        return Atom.button(context, "Update Login Details", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateLoginDetails(item);
            }
        });
    }

    protected abstract void onUpdateLoginDetails(UserLoginDetails item);
}
