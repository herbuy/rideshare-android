package client.ui.display.contacts;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetUsers;
import core.businessobjects.User;
import layers.render.Atom;
import libraries.android.HerbuyAbsListViewer;
import libraries.android.HerbuyStateView;
import retrofit2.Call;
import retrofit2.Response;

public class ContactsToAdd implements HerbuyView{
    private Context context;
    private FrameLayoutBasedHerbuyStateView view;

    public ContactsToAdd(Context context) {
        this.context = context;
        view = new FrameLayoutBasedHerbuyStateView(context);
        view.getView().setBackgroundColor(Color.WHITE);
        view.getView().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        refresh();
    }

    private void refresh() {
        view.render(busy());
        Rest.api().getUsers(params()).enqueue(new AppCallback<List<User>>() {
            @Override
            protected void onSuccess(Call<List<User>> call, Response<List<User>> response) {
                if (response.body().size() < 1) {
                    view.render(empty());
                } else {
                    view.render(success(response.body()));
                }
            }

            @Override
            protected void onError(Call<List<User>> call, Throwable t) {
                view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        refresh();
                    }
                });
            }
        });
    }

    private View success(List<User> body) {
        HerbuyAbsListViewer<User> listViewer = new HerbuyAbsListViewer<User>(context) {
            @Override
            protected AbsListView createAbsListView(Context context) {
                return new ListView(context);
            }

            @Override
            protected View createItemView(Context context, User item) {
               return new ContactToAdd(context,item).getView();
                //return Atom.textViewPrimaryBold(context,item.getUserName());
            }
        };
        listViewer.setContent(body);
        return listViewer.getView();
    }

    private View empty() {
        return Atom.centeredText(context,"No People to add");
    }

    private ParamsForGetUsers params() {
        ParamsForGetUsers params = new ParamsForGetUsers();
        params.setSessionId(LocalSession.instance().getId());
        params.setAddableByUserIdEquals(LocalSession.instance().getUserId());
        return params;
    }

    private View busy() {
        return Atom.centeredText(context,"Loading...");
    }

    @Override
    public View getView() {
        return view.getView();
    }
}
