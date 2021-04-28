package client.ui.display.Trip.tt_requests;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.display.Trip.tt_requests.received.ReceivedTTRequestView;
import client.ui.libraries.AdapterForViewList;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetTTRequests;
import core.businessobjects.Proposal;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.HerbuyStateView;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class TripTTRequestsReceivedController implements HerbuyView  {
    Context context;
    Trip trip;

    FrameLayoutBasedHerbuyStateView requestListView;

    public TripTTRequestsReceivedController(Context context, Trip trip) {
        this.context = context;
        this.trip = trip;
        requestListView = new FrameLayoutBasedHerbuyStateView(context);
    }

    @Override
    public View getView() {
        init();
        return requestListView.getView();
    }

    protected void init() {
        requestListView.renderBusy();
        Rest.api().getTTRequests(paramsForGetTTRequests()).enqueue(new AppCallback<List<Proposal>>() {
            @Override
            protected void onSuccess(Call<List<Proposal>> call, Response<List<Proposal>> response) {
                requestListView.render(successView(response.body()));
            }

            @Override
            protected void onError(Call<List<Proposal>> call, Throwable t) {
                requestListView.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        init();
                    }
                });
            }
        });
    }

    private View successView(List<Proposal> body) {
        if(body.size() < 1){
            return notYetReceivedAnyRequests();
        }

        return renderRequests(body);
    }

    private View renderRequests(List<Proposal> body) {
        List<View> viewList = new ArrayList<>();
        for(Proposal request : body){
            viewList.add(createListItem(request));
        }

        BaseAdapter adapter = new AdapterForViewList(viewList);
        ListView listView = new ListView(context);
        listView.setAdapter(adapter);
        listView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return listView;
    }

    private View createListItem(Proposal request) {
        return new ReceivedTTRequestView(context,request).getView();
    }

    private View notYetReceivedAnyRequests() {
        return Atom.textView(context,"Not yet received any requests");
    }

    private ParamsForGetTTRequests paramsForGetTTRequests() {
        ParamsForGetTTRequests paramsForGetTTRequests = new ParamsForGetTTRequests();
        paramsForGetTTRequests.setSessionId(LocalSession.instance().getId());
        paramsForGetTTRequests.setTripIdEquals(trip.getTripId());
        paramsForGetTTRequests.setTargetUserIdEquals(LocalSession.instance().getUserId());
        return paramsForGetTTRequests;
    }
}
