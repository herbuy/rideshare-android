package client.ui.display.contacts;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetContacts;
import core.businessobjects.Contact;
import layers.render.Atom;
import libraries.android.HerbuyAbsListViewer;
import libraries.android.HerbuyStateView;
import retrofit2.Call;
import retrofit2.Response;

public class ListOfMyContacts implements HerbuyView {
    private Context context;
    private FrameLayoutBasedHerbuyStateView view;

    public ListOfMyContacts(Context context) {
        this.context = context;
        view = new FrameLayoutBasedHerbuyStateView(context);
    }

    @Override
    public View getView() {
        refresh();
        return view.getView();
    }

    private void refresh() {
        view.render(busy());
        Rest.api().getContacts(paramsForGetMyContacts()).enqueue(new AppCallback<List<Contact>>() {
            @Override
            protected void onSuccess(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.body().size() < 1) {
                    view.render(noContacts());
                } else {
                    view.render(contacts(response.body()));
                }
            }

            private View noContacts() {
                return Atom.centeredText(context,"No Contacts");
            }

            @Override
            protected void onError(Call<List<Contact>> call, Throwable t) {
                view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        refresh();
                    }
                });
            }
        });
    }

    private View contacts(List<Contact> body) {
        HerbuyAbsListViewer<Contact> listViewer = new HerbuyAbsListViewer<Contact>(context) {
            @Override
            protected AbsListView createAbsListView(Context context) {
                return new ListView(context);
            }

            @Override
            protected View createItemView(Context context, Contact contact) {
                return new ContactManager(context,contact).getView();
            }
        };
        listViewer.setContent(body);
        return listViewer.getView();
    }

    private ParamsForGetContacts paramsForGetMyContacts() {
        ParamsForGetContacts params = new ParamsForGetContacts();
        params.setSessionId(LocalSession.instance().getId());
        params.setUserId(LocalSession.instance().getUserId());
        return params;
    }

    private View busy() {
        return Atom.centeredText(context, "Loading contacts...");
    }
}
