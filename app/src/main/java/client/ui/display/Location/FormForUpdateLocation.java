package client.ui.display.Location;

import android.content.Context;
import android.text.Editable;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForUpdateLocation;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import libraries.android.MultiStateNetworkButton;
import retrofit2.Call;
import retrofit2.Response;

public abstract class FormForUpdateLocation implements HerbuyView {
    private Context context;
    private ParamsForUpdateLocation newLocationDetails = new ParamsForUpdateLocation();
    private Location location;

    public FormForUpdateLocation(Context context, Location location) {
        this.context = context;
        this.location = location;
    }


    @Override
    public View getView() {
        //return Atom.textView(context,"Update: "+location.getName());
        newLocationDetails.setLocationId(location.getId());
        newLocationDetails.setName(location.getName());

        return MakeDummy.linearLayoutVertical(
                context,
                name(),
                updateInfo()
        );
    }

    private View updateInfo() {
        return new MultiStateNetworkButton(context){
            String error = "";
            @Override
            protected View defaultView() {
                return Atom.button(context, "Update", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doUpdateInfo();
                    }

                    private void doUpdateInfo() {
                        changeViewToBusy();
                        Rest.api().updateLocation(newLocationDetails).enqueue(new AppCallback<List<Location>>() {
                            @Override
                            protected void onSuccess(Call<List<Location>> call, Response<List<Location>> response) {
                                changeViewToSuccess();
                                onLocationUpdated(response.body().get(0));
                            }

                            @Override
                            protected void onError(Call<List<Location>> call, Throwable t) {
                                error = t.getMessage();
                                changeViewToFailed();
                            }
                        });
                    }
                });
            }

            @Override
            protected View busyView() {
                return Atom.textView(context,"Updating location...");
            }

            @Override
            protected View successView() {
                return Atom.textView(context,"Location updated.");
            }

            @Override
            protected View errorView() {
                return Atom.textView(context,error);
            }
        }.getView();

    }

    protected abstract void onLocationUpdated(Location location);

    private View name() {
        return Atom.editText(context, "New Name", location.getName(), new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                newLocationDetails.setName(s.toString());
            }
        });
    }
}
