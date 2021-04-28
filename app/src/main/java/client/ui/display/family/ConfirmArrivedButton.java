package client.ui.display.family;

import android.content.Context;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest2;
import client.ui.GotoActivity;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForNotifyCompleted;
import core.businessobjects.Family;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.HerbuyStateView;
import retrofit2.Call;
import retrofit2.Response;

public class ConfirmArrivedButton implements HerbuyView {
    private final Context context;
    private final Family family;
    FrameLayoutBasedHerbuyStateView view;


    public ConfirmArrivedButton(Context context, Family family) {
        this.context = context;
        this.family = family;
        view = new FrameLayoutBasedHerbuyStateView(context);

    }

    @Override
    public View getView() {
        init();
        return view.getView();

    }

    private void init() {
        view.render(sendReportButton());
    }

    private View sendReportButton() {
        return Atom.button(context, "Send Arrival Report", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReportArrived();
            }
        });
    }

    private void doReportArrived() {
        view.render(busy());
        Rest2.api().notifyCompleted(paramsForNotifyArrived()).enqueue(new AppCallback<List<Family>>() {
            @Override
            protected void onSuccess(Call<List<Family>> call, Response<List<Family>> response) {
                view.render(success());
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GotoActivity.completedTrips(context);
                    }
                }, 1000);
            }

            @Override
            protected void onError(Call<List<Family>> call, Throwable t) {
                view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        doReportArrived();
                    }
                });
            }
        });
    }

    private View success() {
        return MakeDummy.wrapContent(Atom.textViewPrimaryNormal(context,"Done"));
    }

    private ParamsForNotifyCompleted paramsForNotifyArrived() {
        ParamsForNotifyCompleted params = new ParamsForNotifyCompleted();
        params.setSessionId(LocalSession.instance().getId());
        params.setFamilyId(family.getFamilyId());
        return params;
    }

    private View busy() {
        return MakeDummy.wrapContent(Atom.textView(context,"Sending..."));

    }

}
