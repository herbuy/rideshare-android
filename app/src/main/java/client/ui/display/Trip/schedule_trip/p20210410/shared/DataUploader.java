package client.ui.display.Trip.schedule_trip.p20210410.shared;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest2;
import client.ui.GotoActivity;
import client.ui.display.Trip.TripScheduledEvent;
import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MessageBox;
import retrofit2.Call;
import retrofit2.Response;

public abstract class DataUploader {
    private Button btn;
    public DataUploader(Context context) {
        this.context = context;

        btn = Atom.button(context, defaultText(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParamsForScheduleTrip  params = getData();
                params.setSessionId(LocalSession.instance().getId());
                params.setForUserId(LocalSession.instance().getUserId());

                btn.setClickable(false);
                btn.setText("Wait...");
                upload(params);

            }
        });
    }

    protected abstract ParamsForScheduleTrip getData();

    private Context context;

    public View getView(){
        return btn;
    }

    public void upload(ParamsForScheduleTrip data){

        Rest2.api().scheduleTrip(getData()).enqueue(new AppCallback<List<Trip>>() {
            @Override
            protected void onSuccess(Call<List<Trip>> call, final Response<List<Trip>> response) {
                DataUploader.this.onSuccess(response);
            }

            @Override
            protected void onError(Call<List<Trip>> call, Throwable t) {
                DataUploader.this.onError(t);

            }
        });
    }

    private void onSuccess(Response<List<Trip>> response) {
        btn.setClickable(true);
        btn.setText("Successful");
        btn.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setText(defaultText());
            }
        },2000);
        Trip lastTripScheduled = response.body().get(0);
        LocalSession.instance().setLastTripScheduled(lastTripScheduled);
        GotoActivity.tripDetails(context, lastTripScheduled);
        TripScheduledEvent.instance().notifyObservers(lastTripScheduled);
    }

    private void onError(Throwable t) {
        MessageBox.show(t.getMessage(),context);
        btn.setClickable(true);
        btn.setText(defaultText());
    }

    private String defaultText() {
        return "Submit";
    }
}
