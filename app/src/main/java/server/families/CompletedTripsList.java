package server.families;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest2;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.display.family.FamilyListItem;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetFamilies;
import core.businessobjects.Family;
import layers.render.Atom;
import libraries.android.HerbuyAbsListViewer;
import libraries.android.HerbuyStateView;
import retrofit2.Call;
import retrofit2.Response;

public class CompletedTripsList implements HerbuyView {
    Context context;
    FrameLayoutBasedHerbuyStateView view;



    public CompletedTripsList(Context context) {
        this.context = context;
        view = new FrameLayoutBasedHerbuyStateView(context);
        view.getView().setBackgroundColor(Color.WHITE);
        view.getView().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

    @Override
    public View getView() {

        init();
        return view.getView();
    }

    private void init() {
        view.render(busy());
        Rest2.api().getFamilies(params()).enqueue(new AppCallback<List<Family>>() {
            @Override
            protected void onSuccess(Call<List<Family>> call, Response<List<Family>> response) {
                if (response.body() == null || response.body().size() < 1) {
                    view.render(empty());
                } else {
                    view.render(listOfTrips(response.body()));
                }
            }

            @Override
            protected void onError(Call<List<Family>> call, Throwable t) {
                view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        init();
                    }
                });
            }
        });
    }

    private ParamsForGetFamilies params() {
        ParamsForGetFamilies params = new ParamsForGetFamilies();
        params.setSessionId(LocalSession.instance().getId());
        params.setCompletedByUserId(LocalSession.instance().getUserId());
        return params;
    }

    private View empty() {
        return Atom.centeredText(context, "Not found. Completed trips will appear here");
    }

    private View busy() {
        return Atom.centeredText(context, "Loading...");
    }

    private View listOfTrips(List<Family> body) {
        HerbuyAbsListViewer<Family> listViewer = new HerbuyAbsListViewer<Family>(context) {
            @Override
            protected AbsListView createAbsListView(Context context) {
                return new ListView(context);
            }

            @Override
            protected View createItemView(Context context, Family item) {
                return new FamilyListItem(context,item).getView();
            }

        };
        listViewer.setContent(body);
        return listViewer.getView();
    }
}
